package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AgregarEditarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textID;
	private JTextField textNombre;
	private JTextField textEmail;
	private JButton guardarButton;
	private JButton cancelButton;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AgregarEditarCliente dialog = new AgregarEditarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarEditarCliente() {
		setTitle("Nuevo/Editar Cliente");
		setModal(true);
		setBounds(100, 100, 382, 265);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblIdentificacion = new JLabel("ID:");
			lblIdentificacion.setBounds(78, 48, 48, 14);
			contentPanel.add(lblIdentificacion);
		}
		{
			JLabel lblNombre = new JLabel("Nombre:");
			lblNombre.setBounds(78, 92, 48, 14);
			contentPanel.add(lblNombre);
		}
		{
			JLabel lblEmail = new JLabel("Email:");
			lblEmail.setBounds(78, 142, 48, 14);
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
