package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class DetalleProducto extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField textCodigo;
	private JTextField textNombre;
	private JTextField textExistencias;
	private JTextField textPrecio;
	private JComboBox<String> comboBoxUnidad;
	private JButton guardarButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetalleProducto dialog = new DetalleProducto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetalleProducto() {
		setTitle("Detalle del Producto");
		setModal(true); 
		setBounds(100, 100, 395, 315);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCodigo.setBounds(50, 35, 70, 14);
		contentPanel.add(lblCodigo);
		
		textCodigo = new JTextField();
		textCodigo.setBounds(140, 32, 180, 20);
		contentPanel.add(textCodigo);
		textCodigo.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(50, 75, 70, 14);
		contentPanel.add(lblNombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(140, 72, 180, 20);
		contentPanel.add(textNombre);
		textNombre.setColumns(10);
		
		JLabel lblExistencias = new JLabel("Existencias:");
		lblExistencias.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExistencias.setBounds(50, 115, 80, 14);
		contentPanel.add(lblExistencias);
		
		textExistencias = new JTextField();
		textExistencias.setBounds(140, 112, 180, 20);
		contentPanel.add(textExistencias);
		textExistencias.setColumns(10);
		
		JLabel lblUnidad = new JLabel("Unidad:");
		lblUnidad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUnidad.setBounds(50, 155, 70, 14);
		contentPanel.add(lblUnidad);
		
		comboBoxUnidad = new JComboBox<String>();
		comboBoxUnidad.setModel(new DefaultComboBoxModel<>(new String[] {"kg", "l", "m", "cm", "unidades"}));
		comboBoxUnidad.setBounds(140, 151, 180, 22);
		contentPanel.add(comboBoxUnidad);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrecio.setBounds(50, 195, 70, 14);
		contentPanel.add(lblPrecio);
		
		textPrecio = new JTextField();
		textPrecio.setBounds(140, 192, 180, 20);
		contentPanel.add(textPrecio);
		textPrecio.setColumns(10);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				guardarButton = new JButton("Guardar");
				guardarButton.setActionCommand("OK");
				buttonPane.add(guardarButton);
				getRootPane().setDefaultButton(guardarButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}