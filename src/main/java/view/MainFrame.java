package view;

import ShimmerAPI.Conexion;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import entity.Port;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import util.ActivePorts;
import util.FileCSV;
import util.UpdateComponentsThread;

public class MainFrame extends javax.swing.JFrame  {

    private final Conexion con;
    private final ActivePorts controllerPorts;
    private List<Port> portsEnables;
    private String selectedPort;
    private UpdateComponentsThread update_Thread;
    private FileCSV file_CSV;

    public MainFrame() {
        controllerPorts = new ActivePorts();
        portsEnables = new ArrayList<>();
        initComponents();
        fillComponents();
        con = new Conexion();
        
        btnPause.setVisible(false);
        btnStop.setVisible(false);
    }

    public Conexion getCon() {
        return con;
    }

    public JLabel getLbEstado() {
        return lbEstado;
    }

    public JProgressBar getBarBattery() {
        return barBattery;
    }

    public JButton getBtnConect() {
        return btnConect;
    }

    public JButton getBtnDisconect() {
        return btnDisconect;
    }

    public JButton getBtnPause() {
        return btnPause;
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }

    public JButton getBtnStop() {
        return btnStop;
    }

    public JRadioButton getChkGSR() {
        return chkGSR;
    }

    public JRadioButton getChkPPG() {
        return chkPPG;
    }
    
    /**
     * Llena los componentes por defecto del formaulario.
     */
    private void fillComponents() {
        loadPortsComm();
    }

    /**
     * Lee los puertos obtenidos en la lista de tipo {@link Port}, para obtener
     * solo su descripción y mostrarla en el ComboBox de los puertos.
     */
    private void loadPortsComm() {
        List<String> listPornames = new ArrayList<>();
        portsEnables = controllerPorts.getPorts();
        for (Port portsEnable : portsEnables) {
            listPornames.add(portsEnable.getDescripción());
        }
        fillCombo(cmbPorts, listPornames);
    }

    /**
     * Método para llenar un ComboBox con datos de una lista de cadenas.
     * @param combo ComboBox que se llenará
     * @param lista Lista de elementos para insertar en el ComboBox.
     */
    private void fillCombo(JComboBox<String> combo, List<String> lista) {
        combo.removeAllItems();
        if (lista.isEmpty()) {
            combo.addItem("No hay puertos conectados.");
        } else {            
            for (String elem : lista) {
                combo.addItem(elem);
            }
        }
    }
    
    /**
     * Crea la conexión con el dispositivo Shimmer y inicia el hilo para 
     * actualizar los componentes del frame segun el estado.
     */
    private void createConexion() {        
        con.setDeviceComPort(selectedPort);
        con.conectar();
        update_Thread = new UpdateComponentsThread(this);
        Thread hilo = new Thread(update_Thread);
        hilo.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlPrincipal = new javax.swing.JPanel();
        pnlDispositivos = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbPorts = new javax.swing.JComboBox<>();
        btnConect = new javax.swing.JButton();
        chkGSR = new javax.swing.JRadioButton();
        chkPPG = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        barBattery = new javax.swing.JProgressBar();
        btnReload = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        lbEstado = new javax.swing.JLabel();
        btnDisconect = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pnlDatosPaciente = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbSexo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtProfesion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cmbSemestre = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cmbProcedencia = new javax.swing.JComboBox<>();
        pnlArchivo = new javax.swing.JPanel();
        btnChoosePathFile = new javax.swing.JButton();
        txtNameFile = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMarks = new javax.swing.JTable();
        btnAddMark = new javax.swing.JButton();
        pnlBotnes = new javax.swing.JPanel();
        btnStop = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnPlay = new javax.swing.JButton();
        pnlTransmision = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Shimmer Computer Intelligent");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>\n<style>\n      p {color:orange;text-align:center;}\n    </style>\n<p>Cuerpo académico de <br> \nsistemas computacionales Inteligentes<p>\n</html>"); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("logo");

        pnlPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración del experimento"));
        pnlPrincipal.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 14)); // NOI18N
        java.awt.GridBagLayout pnlPrincipalLayout = new java.awt.GridBagLayout();
        pnlPrincipalLayout.columnWidths = new int[] {0, 5, 0};
        pnlPrincipalLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0};
        pnlPrincipal.setLayout(pnlPrincipalLayout);

        pnlDispositivos.setBorder(javax.swing.BorderFactory.createTitledBorder("Dispositivo y sensores"));
        pnlDispositivos.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 14)); // NOI18N

        jLabel3.setText("Seleccion el puerto del dispositivo");

        cmbPorts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPortsActionPerformed(evt);
            }
        });

        btnConect.setText("Conectar");
        btnConect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectActionPerformed(evt);
            }
        });

        chkGSR.setText("GSR");

        chkPPG.setText("PPG/HR");

        jLabel5.setText("Bateria");

        barBattery.setStringPainted(true);

        btnReload.setText("R");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        jLabel11.setText("Estado:");

        lbEstado.setBackground(new java.awt.Color(102, 102, 102));
        lbEstado.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        lbEstado.setForeground(new java.awt.Color(204, 0, 51));
        lbEstado.setText("Desconectado");

        btnDisconect.setText("Desconectar");
        btnDisconect.setEnabled(false);
        btnDisconect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconectActionPerformed(evt);
            }
        });

        jLabel15.setText("jLabel15");

        jLabel16.setText("jLabel16");

        javax.swing.GroupLayout pnlDispositivosLayout = new javax.swing.GroupLayout(pnlDispositivos);
        pnlDispositivos.setLayout(pnlDispositivosLayout);
        pnlDispositivosLayout.setHorizontalGroup(
            pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDispositivosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlDispositivosLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barBattery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlDispositivosLayout.createSequentialGroup()
                        .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDispositivosLayout.createSequentialGroup()
                                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(cmbPorts, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReload)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConect))
                            .addGroup(pnlDispositivosLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbEstado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDisconect))
                    .addGroup(pnlDispositivosLayout.createSequentialGroup()
                        .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkGSR)
                            .addComponent(chkPPG))
                        .addGap(29, 29, 29)
                        .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDispositivosLayout.setVerticalGroup(
            pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDispositivosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnConect)
                        .addComponent(btnReload)
                        .addComponent(btnDisconect))
                    .addGroup(pnlDispositivosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPorts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barBattery, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkGSR)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkPPG)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        pnlPrincipal.add(pnlDispositivos, gridBagConstraints);

        pnlDatosPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del paciente"));
        pnlDatosPaciente.setToolTipText("");

        jLabel4.setText("Edad");

        jLabel6.setText("Sexo");

        cmbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        jLabel7.setText("Carrera/Profesión");

        jLabel8.setText("Semestre");

        cmbSemestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Octavo", "Noveno", "Décimo" }));

        jLabel13.setText("Procedencia");

        cmbProcedencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sierra Sur", "Cañada", "Costa", "Istmo", "Mixteca", "Papaloapan", "Sierra Norte", "Valles Centrales" }));

        javax.swing.GroupLayout pnlDatosPacienteLayout = new javax.swing.GroupLayout(pnlDatosPaciente);
        pnlDatosPaciente.setLayout(pnlDatosPacienteLayout);
        pnlDatosPacienteLayout.setHorizontalGroup(
            pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7)
                    .addComponent(txtProfesion)
                    .addComponent(jLabel8)
                    .addComponent(cmbSemestre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13)
                    .addComponent(cmbProcedencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlDatosPacienteLayout.setVerticalGroup(
            pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProfesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlPrincipal.add(pnlDatosPaciente, gridBagConstraints);

        pnlArchivo.setBorder(javax.swing.BorderFactory.createTitledBorder("Archivo y marcadores"));

        btnChoosePathFile.setText("...");
        btnChoosePathFile.setToolTipText("Haz clilck para seleccionar la ruta del archivo.");
        btnChoosePathFile.setEnabled(false);
        btnChoosePathFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoosePathFileActionPerformed(evt);
            }
        });

        txtNameFile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameFileKeyPressed(evt);
            }
        });

        jLabel9.setText("Nombre del archivo");
        jLabel9.setToolTipText("");

        jLabel10.setText("Marcadores");

        tblMarks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Marca", "Descripción"
            }
        ));
        jScrollPane1.setViewportView(tblMarks);
        if (tblMarks.getColumnModel().getColumnCount() > 0) {
            tblMarks.getColumnModel().getColumn(0).setResizable(false);
            tblMarks.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblMarks.getColumnModel().getColumn(1).setResizable(false);
        }

        btnAddMark.setText("+");
        btnAddMark.setToolTipText("Agregar marcador.");
        btnAddMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMarkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlArchivoLayout = new javax.swing.GroupLayout(pnlArchivo);
        pnlArchivo.setLayout(pnlArchivoLayout);
        pnlArchivoLayout.setHorizontalGroup(
            pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlArchivoLayout.createSequentialGroup()
                        .addComponent(txtNameFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChoosePathFile))
                    .addGroup(pnlArchivoLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 127, Short.MAX_VALUE))
                    .addGroup(pnlArchivoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddMark)))
                .addContainerGap())
        );
        pnlArchivoLayout.setVerticalGroup(
            pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoosePathFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(btnAddMark))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.1;
        pnlPrincipal.add(pnlArchivo, gridBagConstraints);

        btnStop.setText("Terminar");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        pnlBotnes.add(btnStop);

        btnPause.setText("Pausar");
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });
        pnlBotnes.add(btnPause);

        btnPlay.setText("Iniciar");
        btnPlay.setEnabled(false);
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });
        pnlBotnes.add(btnPlay);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlPrincipal.add(pnlBotnes, gridBagConstraints);

        pnlTransmision.setBorder(javax.swing.BorderFactory.createTitledBorder("Transmisión"));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tiempo del experimento:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 102, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("00:00:00");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 102, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("00:00:00");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Tiempo del experimento:");

        javax.swing.GroupLayout pnlTransmisionLayout = new javax.swing.GroupLayout(pnlTransmision);
        pnlTransmision.setLayout(pnlTransmisionLayout);
        pnlTransmisionLayout.setHorizontalGroup(
            pnlTransmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTransmisionLayout.createSequentialGroup()
                .addGroup(pnlTransmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTransmisionLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTransmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTransmisionLayout.setVerticalGroup(
            pnlTransmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTransmisionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTransmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTransmisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        pnlPrincipal.add(pnlTransmision, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        loadPortsComm();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void cmbPortsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPortsActionPerformed
        
        if(cmbPorts.getItemCount()>0){
            selectedPort = portsEnables.get(
                    cmbPorts.getSelectedIndex()).getNombre();
            System.out.println("puerto seleccionado: " + selectedPort);
        }
        
    }//GEN-LAST:event_cmbPortsActionPerformed

    private void btnConectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectActionPerformed
        createConexion();
    }//GEN-LAST:event_btnConectActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        List<String> cabeceras = new ArrayList<>();
        cabeceras.add("Edad");
        cabeceras.add("Semestre");
        cabeceras.add("Sexo");
        cabeceras.add("Profesión");
        cabeceras.add("Procedencia");
        file_CSV.openFile();
        file_CSV.setColumns(cabeceras);
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPauseActionPerformed
        
    }//GEN-LAST:event_btnPauseActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        file_CSV.closeFile();
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnDisconectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconectActionPerformed
        con.desconectar();
    }//GEN-LAST:event_btnDisconectActionPerformed

    private void btnAddMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMarkActionPerformed

    }//GEN-LAST:event_btnAddMarkActionPerformed

    private void txtNameFileKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameFileKeyPressed
        if(txtNameFile.getText().isEmpty()){
            btnChoosePathFile.setEnabled(false);
        }else{
            btnChoosePathFile.setEnabled(true);
        }
    }//GEN-LAST:event_txtNameFileKeyPressed

    private void btnChoosePathFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoosePathFileActionPerformed
        JFileChooser fileChooser =  new JFileChooser(
            FileSystemView.getFileSystemView().
            getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Mostrar el cuadro de diálogo para seleccionar una ruta o archivo
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Obtener la ruta selecionada
            File selectedFile = fileChooser.getSelectedFile();

            // Creando el objeto para el archivo CSV.
            file_CSV =  new FileCSV(selectedFile.getAbsolutePath()+"\\", 
                txtNameFile.getText());
        }
    }//GEN-LAST:event_btnChoosePathFileActionPerformed

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barBattery;
    private javax.swing.JButton btnAddMark;
    private javax.swing.JButton btnChoosePathFile;
    private javax.swing.JButton btnConect;
    private javax.swing.JButton btnDisconect;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnStop;
    private javax.swing.JRadioButton chkGSR;
    private javax.swing.JRadioButton chkPPG;
    private javax.swing.JComboBox<String> cmbPorts;
    private javax.swing.JComboBox<String> cmbProcedencia;
    private javax.swing.JComboBox<String> cmbSemestre;
    private javax.swing.JComboBox<String> cmbSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JPanel pnlArchivo;
    private javax.swing.JPanel pnlBotnes;
    private javax.swing.JPanel pnlDatosPaciente;
    private javax.swing.JPanel pnlDispositivos;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlTransmision;
    private javax.swing.JTable tblMarks;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtNameFile;
    private javax.swing.JTextField txtProfesion;
    // End of variables declaration//GEN-END:variables
}
