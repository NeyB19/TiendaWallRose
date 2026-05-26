package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import control.Controladora;
import logica.Cliente;
import logica.OrdenCompra;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InformacionCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableOrdenesDelCliente;
	private JLabel lblIdentificacionCliente;
	private JLabel lblNombreDelCliente;
	private JLabel lblEmailDelCliente;
	private JButton btnTodasLasOrdenes;
	private JButton btnOrdenesIniciadas;
	private JButton btnOrdenesPendientes;
	private JButton btnOrdenesTerminadas;
	private String idCliente;
	private JLabel lblTotalPendiente;
	private JButton regresarButton;

	/**
	 * Launch the application.
	 */
	
	private void cargarDatosCliente() {
		Controladora control = Controladora.getInstance();
		try {
			Cliente c = control.obtenerCliente(idCliente);
			lblIdentificacionCliente.setText(c.getId());
			lblNombreDelCliente.setText(c.getNombre());
			lblEmailDelCliente.setText(c.getEmail());
			
			double totalPendiente = control.obtenerMontoTotalPendientesCliente(idCliente);
			lblTotalPendiente.setText("¢" + totalPendiente);
			
			filtrarOrdenes("Todas");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					this, 
					"Error al cargar datos del cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void filtrarOrdenes(String filtro) {
		Controladora control = Controladora.getInstance();
		try {
			List<OrdenCompra> listaOrdenes;
			
			switch (filtro) {
				case "Iniciadas":
					listaOrdenes = control.obtenerListadoOrdenesIniciadasCliente(idCliente);
					break;
				case "Pendientes":
					listaOrdenes = control.obtenerListadoOrdenesPendientesCliente(idCliente);
					break;
				case "Terminadas":
					listaOrdenes = control.obtenerListadoOrdenesTerminadasCliente(idCliente);
					break;
				default: 
					listaOrdenes = control.obtenerListadoOrdenesCliente(idCliente);
					break;
			}
			cargarOrdenesCliente(listaOrdenes);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					this, 
					"Error al filtrar las órdenes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void cargarOrdenesCliente(List<OrdenCompra> lista) {
		DefaultTableModel model = (DefaultTableModel) tableOrdenesDelCliente.getModel();
		model.setRowCount(0);
		
		for (OrdenCompra o : lista) {
			Object[] fila = new Object[] { o.getNumero(), o.getFecha(), o.getEstado() };
			model.addRow(fila);			
		}			
	}

	/**
	 * Create the dialog.
	 */
	public InformacionCliente(String idCliente) {
		this.idCliente = idCliente;
		
		setModal(true);
		setTitle("Información del Cliente");
		setBounds(100, 100, 496, 371);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarDatosCliente();
			}
		});
		
		JLabel lblIDCliente = new JLabel("ID: ");
		lblIDCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIDCliente.setBounds(10, 11, 48, 14);
		contentPanel.add(lblIDCliente);
		
		lblIdentificacionCliente = new JLabel("---");
		lblIdentificacionCliente.setBounds(78, 11, 122, 14);
		contentPanel.add(lblIdentificacionCliente);
		
		JLabel lblNombreCliente = new JLabel("Nombre:");
		lblNombreCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreCliente.setBounds(10, 30, 57, 14);
		contentPanel.add(lblNombreCliente);
		
		JLabel lblEmailCliente = new JLabel("Email:");
		lblEmailCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmailCliente.setBounds(10, 49, 48, 14);
		contentPanel.add(lblEmailCliente);
		
		lblNombreDelCliente = new JLabel("---");
		lblNombreDelCliente.setBounds(78, 30, 219, 14);
		contentPanel.add(lblNombreDelCliente);
		
		lblEmailDelCliente = new JLabel("---");
		lblEmailDelCliente.setBounds(78, 49, 160, 14);
		contentPanel.add(lblEmailDelCliente);
		
		JLabel lblListaOrdenesCliente = new JLabel("Lista de órdenes:");
		lblListaOrdenesCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListaOrdenesCliente.setBounds(10, 84, 111, 14);
		contentPanel.add(lblListaOrdenesCliente);
		
		JScrollPane scrollPane_OrdenesCliente = new JScrollPane();
		scrollPane_OrdenesCliente.setBounds(10, 109, 340, 154);
		contentPanel.add(scrollPane_OrdenesCliente);
		
		tableOrdenesDelCliente = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00FAmero", "Fecha", "Estado"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		tableOrdenesDelCliente.setModel(dataModel);
		tableOrdenesDelCliente.getColumnModel().getColumn(0).setPreferredWidth(97);
		tableOrdenesDelCliente.getColumnModel().getColumn(1).setPreferredWidth(116);
		tableOrdenesDelCliente.getColumnModel().getColumn(2).setPreferredWidth(118);
		scrollPane_OrdenesCliente.setViewportView(tableOrdenesDelCliente);
		
		btnTodasLasOrdenes = new JButton("Todas");
		btnTodasLasOrdenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarOrdenes("Todas");
			}
		});
		btnTodasLasOrdenes.setBounds(373, 116, 99, 22);
		contentPanel.add(btnTodasLasOrdenes);
		
		btnOrdenesIniciadas = new JButton("Iniciadas");
		btnOrdenesIniciadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarOrdenes("Iniciadas");
			}
		});
		btnOrdenesIniciadas.setBounds(373, 151, 99, 22);
		contentPanel.add(btnOrdenesIniciadas);
		
		btnOrdenesPendientes = new JButton("Pendientes");
		btnOrdenesPendientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarOrdenes("Pendientes");
			}
		});
		btnOrdenesPendientes.setBounds(373, 184, 99, 22);
		contentPanel.add(btnOrdenesPendientes);
		
		btnOrdenesTerminadas = new JButton("Terminadas");
		btnOrdenesTerminadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarOrdenes("Terminadas");
			}
		});
		btnOrdenesTerminadas.setBounds(371, 217, 101, 22);
		contentPanel.add(btnOrdenesTerminadas);
		
		JLabel lblPendiente = new JLabel("Total pendiente: ");
		lblPendiente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPendiente.setBounds(10, 276, 99, 14);
		contentPanel.add(lblPendiente);
		
		lblTotalPendiente = new JLabel("---");
		lblTotalPendiente.setBounds(125, 276, 101, 14);
		contentPanel.add(lblTotalPendiente);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				regresarButton = new JButton("Regresar");
				regresarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				regresarButton.setActionCommand("Cancel");
				buttonPane.add(regresarButton);
			}
		}
	}
}
