package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import com.chatting.controlador.ControladorCliente;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import com.chatting.ejecutable.ClientePrivado;

/**
 * Ventana del cliente
 *
 */
public class VistaCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JFrame ventana;
	WindowListener exitListener;
	
	private JLabel labelClientes, botonListado;
	private JTextArea chat;
	//private JTextArea Usuarios;
	private JTextField campo;
	private  JList<String> listaUsuarios;
	private JButton botonEnviar, botonSalir, botonLimpiar, botonScroll;
	DefaultCaret caret;
	private JButton btnChatPrivado;
	public JLabel lblBienvenido;
	public Boolean isPrivateChat;
	public String chatReceiver;
	//private JList list;
	
	/* ============================| Constructores |============================ */
	
	public VistaCliente(JFrame ventana) {
		this.ventana = ventana;
		isPrivateChat = false;
		setLayout(new BorderLayout());
		JPanel panelNorte = new JPanel();
		JPanel panelSur = new JPanel(new GridLayout(1,3));
		JPanel panelWest = new JPanel(new BorderLayout());
		
		/* --------------------- Inicializaciones --------------------- */
		chat = new JTextArea();
		chat.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		/*Usuarios= new JTextArea(1,5);
		Usuarios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Usuarios.setLineWrap(true);
		Usuarios.setForeground(Color.black);*/
		campo = new JTextField();
		campo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		botonListado = new JLabel("Listado de clientes");
		botonListado.setFont(new Font("Tahoma", Font.BOLD, 12));
		botonEnviar = new JButton("Enviar");
		botonLimpiar = new JButton("Limpiar chat");
		JScrollPane scroll = new JScrollPane(chat);
		
		listaUsuarios = new JList<String>();
		/* --------------------- Asignaciones --------------------- */
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[]{163, 159, 83, 53, 0};
		gbl_panelNorte.rowHeights = new int[]{23, 0};
		gbl_panelNorte.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelNorte.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelNorte.setLayout(gbl_panelNorte);
		
		lblBienvenido = new JLabel("Bienvenido: ");
		lblBienvenido.setFont(new Font("Arial", Font.BOLD, 11));
		GridBagConstraints gbc_lblBienvenido = new GridBagConstraints();
		gbc_lblBienvenido.anchor = GridBagConstraints.WEST;
		gbc_lblBienvenido.insets = new Insets(0, 0, 0, 5);
		gbc_lblBienvenido.gridx = 0;
		gbc_lblBienvenido.gridy = 0;
		panelNorte.add(lblBienvenido, gbc_lblBienvenido);
		labelClientes = new JLabel("Clientes en el chat: 0/0");
		labelClientes.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_labelClientes = new GridBagConstraints();
		gbc_labelClientes.insets = new Insets(0, 0, 0, 5);
		gbc_labelClientes.gridx = 1;
		gbc_labelClientes.gridy = 0;
		panelNorte.add(labelClientes, gbc_labelClientes);
		panelWest.add(botonListado, BorderLayout.NORTH);
		botonScroll = new JButton("Auto-scroll");
		GridBagConstraints gbc_botonScroll = new GridBagConstraints();
		gbc_botonScroll.anchor = GridBagConstraints.NORTHEAST;
		gbc_botonScroll.insets = new Insets(0, 0, 0, 5);
		gbc_botonScroll.gridx = 2;
		gbc_botonScroll.gridy = 0;
		panelNorte.add(botonScroll, gbc_botonScroll);
		panelSur.add(botonLimpiar);
		panelSur.add(campo);
		//panelWest.add(Usuarios, BorderLayout.CENTER);
		panelSur.add(botonEnviar);
 		
		add(panelNorte, BorderLayout.NORTH);
		botonSalir = new JButton("Salir");
		GridBagConstraints gbc_botonSalir = new GridBagConstraints();
		gbc_botonSalir.anchor = GridBagConstraints.NORTHEAST;
		gbc_botonSalir.gridx = 3;
		gbc_botonSalir.gridy = 0;
		panelNorte.add(botonSalir, gbc_botonSalir);
		add(panelSur, BorderLayout.SOUTH);
		add(panelWest, BorderLayout.WEST);
		
		btnChatPrivado = new JButton("Chat Privado");
		panelWest.add(btnChatPrivado, BorderLayout.SOUTH);
		
		listaUsuarios = new JList<String>();
		panelWest.add(listaUsuarios, BorderLayout.CENTER);
		add(scroll, BorderLayout.CENTER);
		
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret = (DefaultCaret)chat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setPreferredSize(new Dimension(480, 360));
		chat.setLineWrap(true);
		
		chat.setEditable(false);
		setEnabled(false);
	}
	
	/* ============================| Metodos |============================ */
	
	public String getTextoCampo() {
		if (!isPrivateChat){
			return campo.getText().toString();
		}
		return "/PRIVATE" + chatReceiver + " " + campo.getText().toString();
	}
	
	public void appendChat(String msg){
		chat.append(msg + "\n");
	}
	
	public void vaciarTextoCampo() {
		campo.setText("");
	}
	
	public void setClientes(String clientes) {
		labelClientes.setText("Clientes en el chat: "+ clientes);
	}
	
	public void addText(String linea) {
		final StringBuffer sb = new StringBuffer("<SERVER> CLIENTES CONECTADOS:");
		if (linea.contains(sb)) {
			final DefaultListModel<String> model = new DefaultListModel<String>();
			final String[] users = linea.substring(29).split("[\\,\\.]");
			for (int i = 0; i < users.length; i++) {
				model.addElement(users[i]);
			}
			this.listaUsuarios.setModel(model);
		} else {
			chat.append(linea + "\n");
		}
	}
	
	public void limpiarChat() {
		chat.setText("");
	}
	
	/*public void setUsuarios() {
		Usuarios.setText("");
	}*/
	
	public void alternarAutoScroll() {
		if(caret.getUpdatePolicy() != DefaultCaret.NEVER_UPDATE)
			caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		else {
			chat.setCaretPosition(chat.getDocument().getLength() );
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}
	
	public void setEnabled(boolean activado) {
		campo.setEnabled(activado);
		chat.setEnabled(activado);
		botonEnviar.setEnabled(activado);
		botonLimpiar.setEnabled(activado);
		botonListado.setEnabled(activado);
		botonSalir.setEnabled(activado);
		botonScroll.setEnabled(activado);
	}
	
	public void setControlador(ControladorCliente l) {
		botonEnviar.setActionCommand("enviar");
		campo.setActionCommand("enviar");
		botonSalir.setActionCommand("salir");
		botonLimpiar.setActionCommand("limpiar");
		//botonListado.setActionCommand("listado");
		botonScroll.setActionCommand("scroll");
		
		botonEnviar.addActionListener(l);
		campo.addActionListener(l);
		botonSalir.addActionListener(l);
		botonLimpiar.addActionListener(l);
		//botonListado.addActionListener(l);
		botonScroll.addActionListener(l);
		
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				isPrivateChat = isPrivateChat ? false : true; // inverse
				if(isPrivateChat){
					final String s = (String) listaUsuarios.getSelectedValue();
					chatReceiver = s;
					JOptionPane.showMessageDialog(null, "Se inicio un chat privado con" + s);
					//limpiarChat();
				} else {
					JOptionPane.showMessageDialog(null, "Termin el chat privado con" + chatReceiver);
					//limpiarChat();
				}
			}
		});
		
		// Controlador de cierre de ventana (para que se desconecte bien al cerrar)
		exitListener = new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		        l.salir();
		        System.exit(0);
		    }
		};
		ventana.addWindowListener(exitListener);
		
	}	
	
}
