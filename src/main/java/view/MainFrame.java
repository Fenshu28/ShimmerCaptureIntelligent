package view;

import ShimmerAPI.Conexion;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.shimmerresearch.driver.BasicProcessWithCallBack;
import entity.Port;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIManager;
import util.ActivePorts;
import util.UpdateComponentsThread;

public class MainFrame extends javax.swing.JFrame  {

    private Conexion con;
    private final ActivePorts controllerPorts;
    private List<Port> portsEnables;
    private String selectedPort;
    private UpdateComponentsThread update_Thread;

    public MainFrame() {
        controllerPorts = new ActivePorts();
        portsEnables = new ArrayList<>();
        initComponents();
        fillComponents();
        con = new Conexion();
    }

    public Conexion getCon() {
        return con;
    }

    public JLabel getLbEstado() {
        return lbEstado;
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
        fillCombo(cmbPuertos, listPornames);
    }

    /**
     * Método para llenar un ComboBox con datos de una lista de cadenas.
     *
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

    private void createConexion() {        
        con.create(selectedPort);
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
        cmbPuertos = new javax.swing.JComboBox<>();
        btnConectar = new javax.swing.JButton();
        chkGSR = new javax.swing.JRadioButton();
        chkPPG = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        barBateria = new javax.swing.JProgressBar();
        btnRecargar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        lbEstado = new javax.swing.JLabel();
        pnlDatosPaciente = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbSexo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        pnlArchivo = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        pnlBotnes = new javax.swing.JPanel();
        btnInciar = new javax.swing.JButton();
        btnInciar1 = new javax.swing.JButton();
        btnInciar2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        pnlPrincipalLayout.rowHeights = new int[] {0, 5, 0, 5, 0};
        pnlPrincipal.setLayout(pnlPrincipalLayout);

        pnlDispositivos.setBorder(javax.swing.BorderFactory.createTitledBorder("Dispositivo y sensores"));
        pnlDispositivos.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 14)); // NOI18N

        jLabel3.setText("Seleccion el puerto del dispositivo");

        cmbPuertos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPuertosActionPerformed(evt);
            }
        });

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        chkGSR.setText("GSR");

        chkPPG.setText("PPG/HR");

        jLabel5.setText("Bateria");

        btnRecargar.setText("R");
        btnRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecargarActionPerformed(evt);
            }
        });

        jLabel11.setText("Estado:");

        lbEstado.setForeground(new java.awt.Color(204, 0, 51));
        lbEstado.setText("Desconectado");

        javax.swing.GroupLayout pnlDispositivosLayout = new javax.swing.GroupLayout(pnlDispositivos);
        pnlDispositivos.setLayout(pnlDispositivosLayout);
        pnlDispositivosLayout.setHorizontalGroup(
            pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDispositivosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDispositivosLayout.createSequentialGroup()
                        .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPuertos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRecargar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConectar))
                    .addGroup(pnlDispositivosLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbEstado))
                    .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(chkGSR)
                        .addComponent(chkPPG)
                        .addGroup(pnlDispositivosLayout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(barBateria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlDispositivosLayout.setVerticalGroup(
            pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDispositivosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnConectar)
                        .addComponent(btnRecargar))
                    .addGroup(pnlDispositivosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPuertos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDispositivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barBateria, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkGSR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPPG)
                .addContainerGap(440, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        pnlPrincipal.add(pnlDispositivos, gridBagConstraints);

        pnlDatosPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del paciente"));
        pnlDatosPaciente.setToolTipText("");

        jLabel4.setText("Edad");

        jLabel6.setText("Sexo");

        cmbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        jLabel7.setText("Carrera/Profesión");

        jLabel8.setText("Semestre");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Octavo", "Noveno", "Décimo" }));

        jLabel13.setText("Procedencia");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sierra Sur", "Cañada", "Costa", "Istmo", "Mixteca", "Papaloapan", "Sierra Norte", "Valles Centrales" }));

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
                    .addComponent(jTextField2)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(101, Short.MAX_VALUE))
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
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        pnlPrincipal.add(pnlDatosPaciente, gridBagConstraints);

        pnlArchivo.setBorder(javax.swing.BorderFactory.createTitledBorder("Archivo y marcadores"));

        jButton1.setText("...");

        jLabel9.setText("Seleccionar nombre y ruta del archivo");

        jLabel10.setText("Marcadores");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton2.setText("+");

        javax.swing.GroupLayout pnlArchivoLayout = new javax.swing.GroupLayout(pnlArchivo);
        pnlArchivo.setLayout(pnlArchivoLayout);
        pnlArchivoLayout.setHorizontalGroup(
            pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlArchivoLayout.createSequentialGroup()
                        .addComponent(jTextField3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(pnlArchivoLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 113, Short.MAX_VALUE))
                    .addGroup(pnlArchivoLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        pnlArchivoLayout.setVerticalGroup(
            pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        pnlPrincipal.add(pnlArchivo, gridBagConstraints);

        pnlBotnes.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnInciar.setText("Terminar");
        pnlBotnes.add(btnInciar);

        btnInciar1.setText("Pausar");
        pnlBotnes.add(btnInciar1);

        btnInciar2.setText("Iniciar");
        pnlBotnes.add(btnInciar2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlPrincipal.add(pnlBotnes, gridBagConstraints);

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
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecargarActionPerformed
        loadPortsComm();
    }//GEN-LAST:event_btnRecargarActionPerformed

    private void cmbPuertosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPuertosActionPerformed
        selectedPort = portsEnables.get(cmbPuertos.getSelectedIndex()).getNombre();
        System.out.println("puerto seleccionado: " + selectedPort);
    }//GEN-LAST:event_cmbPuertosActionPerformed

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        createConexion();
    }//GEN-LAST:event_btnConectarActionPerformed

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barBateria;
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnInciar;
    private javax.swing.JButton btnInciar1;
    private javax.swing.JButton btnInciar2;
    private javax.swing.JButton btnRecargar;
    private javax.swing.JRadioButton chkGSR;
    private javax.swing.JRadioButton chkPPG;
    private javax.swing.JComboBox<String> cmbPuertos;
    private javax.swing.JComboBox<String> cmbSexo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JPanel pnlArchivo;
    private javax.swing.JPanel pnlBotnes;
    private javax.swing.JPanel pnlDatosPaciente;
    private javax.swing.JPanel pnlDispositivos;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTextField txtEdad;
    // End of variables declaration//GEN-END:variables
}
