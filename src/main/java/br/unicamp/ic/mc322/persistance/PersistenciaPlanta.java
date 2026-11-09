package br.unicamp.ic.mc322.persistance;

import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.StatusMaquina;
import br.unicamp.ic.mc322.model.maquina.EnchiMax;
import br.unicamp.ic.mc322.model.maquina.Maquina;
import br.unicamp.ic.mc322.model.maquina.partes.BicoDeInjecao;
import br.unicamp.ic.mc322.service.GerenciadorProducao;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaPlanta {

    private static final String CAMINHO_ARQUIVO = "planta_salva.xml";

    public void salvar(GerenciadorProducao fabrica) throws PersistenciaException {
        salvar(fabrica, CAMINHO_ARQUIVO);
    }

    public void salvar(GerenciadorProducao fabrica, String caminhoArquivo) throws PersistenciaException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("planta");
            doc.appendChild(raiz);

            // Salva o Budget
            Element budget = doc.createElement("budget");
            budget.setTextContent(String.valueOf(fabrica.getBudget()));
            raiz.appendChild(budget);

            // Salva as Máquinas
            Element maquinasNode = doc.createElement("maquinas");
            for (Maquina m : fabrica.getLinha().getMaquinas()) {
                if (m instanceof EnchiMax) {
                    EnchiMax e = (EnchiMax) m;
                    
                    Element mNode = doc.createElement("maquina");
                    mNode.setAttribute("tipo", "EnchiMax");
                    mNode.setAttribute("nome", e.getNome());
                    mNode.setAttribute("saude", String.valueOf(e.getSaude()));
                    mNode.setAttribute("status", e.getStatus().name());

                    Element motorNode = doc.createElement("motor");
                    motorNode.setAttribute("rpm", String.valueOf(e.getRpmMotor()));
                    motorNode.setAttribute("rpmAlvo", String.valueOf(e.getRpmAlvoMotor())); // CORRIGIDO: Adicionado rpmAlvo
                    motorNode.setAttribute("operacional", String.valueOf(e.isMotorOperacional()));
                    motorNode.setAttribute("desgaste", String.valueOf(e.getDesgasteMotor()));
                    mNode.appendChild(motorNode);

                    Element sensorNode = doc.createElement("sensor");
                    sensorNode.setAttribute("calibrado", String.valueOf(e.isSensorCalibrado()));
                    sensorNode.setAttribute("leituras", String.valueOf(e.getLeiturasSensor()));
                    mNode.appendChild(sensorNode);

                    Element bicosNode = doc.createElement("bicos");
                    for (BicoDeInjecao b : e.getBicosParaSalvar()) {
                        Element bicoNode = doc.createElement("bico");
                        bicoNode.setAttribute("id", String.valueOf(b.getId()));
                        bicoNode.setAttribute("entupido", String.valueOf(b.isEntupido()));
                        bicosNode.appendChild(bicoNode);
                    }
                    mNode.appendChild(bicosNode);

                    maquinasNode.appendChild(mNode);
                }
            }
            raiz.appendChild(maquinasNode);

            // Salva Demandas
            Element demandasNode = doc.createElement("demandas");
            for (Demanda d : fabrica.getDemandas()) {
                Element dNode = doc.createElement("demanda");
                dNode.setAttribute("nome", d.getNome());
                dNode.setAttribute("quantidade", String.valueOf(d.getQuantidade()));
                dNode.setAttribute("status", d.getStatus().name());
                demandasNode.appendChild(dNode);
            }
            raiz.appendChild(demandasNode);

            // Escreve o arquivo físico no disco
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(caminhoArquivo));
            transformer.transform(source, result);

            System.out.println("Planta salva com sucesso em " + caminhoArquivo);

        } catch (Exception e) {
            throw new PersistenciaException("Erro ao salvar a planta: " + e.getMessage(), e);
        }
    }

    public GerenciadorProducao carregar(String caminhoArquivo) throws PersistenciaException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            throw new PersistenciaException("Arquivo não encontrado: " + caminhoArquivo);
        }

        try {
            GerenciadorProducao fabrica = new GerenciadorProducao(new br.unicamp.ic.mc322.model.MateriaPrima(1, "Madeira", 0, "kg", 0.0, (short) 0), br.unicamp.ic.mc322.model.CenarioOperacao.IDEAL);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(arquivo);
            doc.getDocumentElement().normalize();

            // ==========================================
            // FASE 1: LER TUDO PARA MEMÓRIA (Sem alterar a fábrica real)
            // ==========================================
            double budget = Double.parseDouble(doc.getElementsByTagName("budget").item(0).getTextContent());

            List<Maquina> maquinasCarregadas = new ArrayList<>();
            NodeList maquinasList = doc.getElementsByTagName("maquina");
            for (int i = 0; i < maquinasList.getLength(); i++) {
                Element mEl = (Element) maquinasList.item(i);
                
                if ("EnchiMax".equals(mEl.getAttribute("tipo"))) {
                    String nome = mEl.getAttribute("nome");
                    double saude = Double.parseDouble(mEl.getAttribute("saude"));
                    StatusMaquina status = StatusMaquina.valueOf(mEl.getAttribute("status"));

                    Element motorEl = (Element) mEl.getElementsByTagName("motor").item(0);
                    int rpm = Integer.parseInt(motorEl.getAttribute("rpm"));
                    int rpmAlvo = Integer.parseInt(motorEl.getAttribute("rpmAlvo")); // CORRIGIDO: Lendo rpmAlvo
                    boolean motorOp = Boolean.parseBoolean(motorEl.getAttribute("operacional"));
                    double desgaste = Double.parseDouble(motorEl.getAttribute("desgaste"));

                    Element sensorEl = (Element) mEl.getElementsByTagName("sensor").item(0);
                    boolean calibrado = Boolean.parseBoolean(sensorEl.getAttribute("calibrado"));
                    int leituras = Integer.parseInt(sensorEl.getAttribute("leituras"));

                    List<Boolean> bicosEntupidos = new ArrayList<>();
                    NodeList bicosList = mEl.getElementsByTagName("bico");
                    for (int j = 0; j < bicosList.getLength(); j++) {
                        Element bicoEl = (Element) bicosList.item(j);
                        bicosEntupidos.add(Boolean.parseBoolean(bicoEl.getAttribute("entupido")));
                    }

                    // Instancia a máquina e restaura o estado
                    EnchiMax novaEnchiMax = new EnchiMax(nome, 50, 10.0, 0.15);
                    novaEnchiMax.restaurar(saude, status, rpm, rpmAlvo, motorOp, desgaste, calibrado, leituras, bicosEntupidos);
                    
                    maquinasCarregadas.add(novaEnchiMax);
                }
            }
            
            List<Demanda> demandasCarregadas = new ArrayList<>();
            NodeList demandasList = doc.getElementsByTagName("demanda");
            for (int i = 0; i < demandasList.getLength(); i++) {
                Element d = (Element) demandasList.item(i);
                Demanda dem = new Demanda(d.getAttribute("nome"), Integer.parseInt(d.getAttribute("quantidade")));
                // Aqui você pode setar o status: dem.setStatus(StatusDemanda.valueOf(d.getAttribute("status")));
                demandasCarregadas.add(dem);
            }

            // ==========================================
            // FASE 2: SE CHEGOU ATÉ AQUI, O XML É VÁLIDO. APLICAR NA FÁBRICA.
            // ==========================================
            // 1. Limpa a fábrica atual (para não duplicar as máquinas)
            fabrica.limparDemandas();
            fabrica.limparMaquinas(); 

            // 2. Aplica os dados lidos
            fabrica.setBudget(budget);
            for (Demanda d : demandasCarregadas) {
                fabrica.registrarDemanda(d);
            }
            for (Maquina m : maquinasCarregadas) {
                fabrica.adicionarMaquina(m);
            }

            System.out.println("Planta carregada com sucesso!");
            return fabrica;

        } catch (Exception e) {
            // Se der erro na Fase 1, a fábrica nem foi tocada! O estado antigo é preservado.
            throw new PersistenciaException("Erro ao carregar a planta: arquivo corrompido ou inválido.", e);
        }
    }
}