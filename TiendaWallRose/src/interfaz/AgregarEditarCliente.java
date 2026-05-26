package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Controladora;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarEditarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textID;
	private JTextField textNombre;
	private JTextField textEmail;
	private JButton guardarButton;
	private JButton cancelButton;
	
	private VentanaInicial ventanaPrincipal;
	private boolean esAgregar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AgregarEditarCliente dialog = new AgregarEditarCliente(null, true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarEditarCliente(VentanaInicial ventanaPrincipal, boolean esAgregar) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.esAgregar = esAgregar;
		setTitle("Agregar/Editar Cliente");
		setModal(true);
		setBounds(100, 100, 382, 265);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblIdentificacion = new JLabel("ID:");
			lblIdentificacion.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblIdentificacion.setBounds(68, 48, 48, 14);
			contentPanel.add(lblIdentificacion);
		}
		{
			JLabel lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNombre.setBounds(68, 92, 68, 14);
			contentPanel.add(lblNombre);
		}
		{
			JLabel lblEmail = new JLabel("Email:");
			lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblEmail.setBounds(68, 142, 48, 14);
			contentPanel.add(lblEmail);
		}
		
		textID = new JTextField();
		textID.setBounds(136, 45, 175, 20);
		contentPanel.add(textID);
		textID.setColumns(10);
		
		textNombre = new JTextField();
		textNombre.setBounds(136, 89, 175, 20);
		contentPanel.add(textNombre);
		textNombre.setColumns(10);
		{
			textEmail = new JTextField();
			textEmail.setBounds(136, 139, 175, 20);
			contentPanel.add(textEmail);
			textEmail.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				guardarButton = new JButton("Guardar");
				guardarButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guardarDatos();
					}
				});
				guardarButton.setActionCommand("OK");
				buttonPane.add(guardarButton);
				getRootPane().setDefaultButton(guardarButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void guardarDatos() {
		String id = textID.getText().trim();
		String nombre = textNombre.getText().trim();
		String email = textEmail.getText().trim();
		
		if (id.isEmpty() || nombre.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			Controladora control = Controladora.getInstance();
			String mensajeExito;
			
			if (esAgregar) {
				control.crearCliente(id, nombre, email);
				mensajeExito = "Cliente registrado con éxito.";
			} else {
				control.actualizarCliente(id, nombre, email);
				mensajeExito = "Cliente modificado con éxito.";
			}
			
			JOptionPane.showMessageDialog(null, mensajeExito, "Éxito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			
			if (ventanaPrincipal != null) {
				ventanaPrincipal.cargarClientes();
			}
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public JTextField getTextID() {
		return textID;
		}

	public JTextField getTextNombre() {
		return textNombre;
		}

	public JTextField getTextEmail() {
		return textEmail;
		}
	}
