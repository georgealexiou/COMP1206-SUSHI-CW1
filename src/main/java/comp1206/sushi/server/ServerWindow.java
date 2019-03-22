package comp1206.sushi.server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.swing.*;

import comp1206.sushi.common.*;
import comp1206.sushi.mock.MockServer;

/**
 * Provides the Sushi Server user interface
 *
 */
public class ServerWindow extends JFrame implements UpdateListener {

	private static final long serialVersionUID = -4661566573959270000L;
	private ServerInterface server;

	public MockServer database = new MockServer();

	private JPanel imagePanel;

	/**
	 * Create a new server window
	 * @param server instance of server to interact with
	 */
	public ServerWindow(ServerInterface server) {
		super("Sushi Server");
		this.server = server;
		this.setTitle(server.getRestaurantName() + " Server");
		server.addUpdateListener(this);

		//Display window
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

		//default screen
		addMenuBar();
		getDefaultScreen();

		setVisible(true);


		//Start timed updates
		startTimer();
	}
	
	/**
	 * Start the timer which updates the user interface based on the given interval to update all panels
	 */
	public void startTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);     
        int timeInterval = 5;
        
        scheduler.scheduleAtFixedRate(() -> refreshAll(), 0, timeInterval, TimeUnit.SECONDS);
	}
	
	/**
	 * Refresh all parts of the server application based on receiving new data, calling the server afresh
	 */
	public void refreshAll() {

	}
	
	@Override
	/**
	 * Respond to the model being updated by refreshing all data displays
	 */
	public void updated(UpdateEvent updateEvent) {
		refreshAll();
	}

	public void getDefaultScreen(){
		try {
			imagePanel = new JPanel();

			URL url = new URL("https://i.imgur.com/sGMt1R4.png");
			BufferedImage image = ImageIO.read(url);
			JLabel label = new JLabel(new ImageIcon(image));
			imagePanel.add(label);

			add(imagePanel);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}


	}

	public void removeAll(){
		getContentPane().removeAll();

		addMenuBar();

		revalidate();
		repaint();


	}

	public void addUtilityButtons(JPanel buttonPanel){

	}

	public void addMenuBar(){

		JMenuBar menuBar = new JMenuBar();

		menuBar.add(getFoodMenu());
		menuBar.add(getDeliveryMenu());
		menuBar.add(getOtherMenu());

		add(menuBar, new BorderLayout().NORTH);
	}

	public JMenu getFoodMenu() {

		JMenu foodMenu = new JMenu("Food");

		JMenuItem dishSelection = new JMenuItem(new AbstractAction("Dish") {
			public void actionPerformed(ActionEvent e) {

				removeAll();

				String [] columns = {"Name", "Description", "Price", "Restock Threshold", "Restock Amount"};
				String [][] data = new String[10000][5];
				Iterator iter = database.dishes.iterator();

				for(int i = 0; i< database.dishes.size(); i++){
					if(iter.hasNext()) {
						Dish current = (Dish) iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getDescription();
						data[i][2] = current.getPrice().toString();
						data[i][3] = current.getRestockThreshold().toString();
						data[i][4] = current.getRestockAmount().toString();

					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);

				JPanel utilityButtonPanel  = new JPanel();

				JButton dishAdd = new JButton("Add"){
					public void actionPerformed(ActionEvent e) {}
				};

				JButton dishEdit = new JButton("Edit"){
					public void actionPerformed(ActionEvent e) {}
				};

				JButton dishRemove = new JButton("Remove");
				dishRemove.setEnabled(false);

				utilityButtonPanel.add(dishAdd);
				utilityButtonPanel.add(dishEdit);
				utilityButtonPanel.add(dishRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
				repaint();

			}
		});
		JMenuItem ingredientSelection = new JMenuItem(new AbstractAction("Ingredient") {
			public void actionPerformed(ActionEvent e) {

				removeAll();
				String [] columns = {"Name", "Unit", "Supplier", "Restock Threshold", "Restock Amount"};
				String [][] data = new String[10000][5];
				Iterator iter = database.ingredients.iterator();

				for(int i = 0; i< database.ingredients.size(); i++){
					if(iter.hasNext()) {
						Ingredient current = (Ingredient)iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getUnit();
						data[i][2] = current.getSupplier().getName();
						data[i][3] = current.getRestockThreshold().toString();
						data[i][4] = current.getRestockAmount().toString();

					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);



				JPanel utilityButtonPanel  = new JPanel();

				JButton ingredientAdd = new JButton("Add"){
					public void actionPerformed(ActionEvent e) {}
				};

				JButton ingredientEdit = new JButton("Edit");
				ingredientEdit.setEnabled(false);

				JButton ingredientRemove = new JButton("Remove");
				ingredientRemove.setEnabled(false);


				utilityButtonPanel.add(ingredientAdd);
				utilityButtonPanel.add(ingredientEdit);
				utilityButtonPanel.add(ingredientRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
				repaint();

			}
		});
		JMenuItem orderSelection = new JMenuItem(new AbstractAction("Order") {
			public void actionPerformed(ActionEvent e) {

				removeAll();

				String [] columns = {"Name", "Distance", "Status"};
				String [][] data = new String[10000][3];
				Iterator iter = database.orders.iterator();

				for(int i = 0; i< database.orders.size(); i++){
					if(iter.hasNext()) {
						Order current = (Order)iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getDistance().toString();
						data[i][2] = current.getStatus();
					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);

				JPanel utilityButtonPanel  = new JPanel();

				JButton orderAdd = new JButton("Add");
				orderAdd.setEnabled(false);

				JButton orderEdit = new JButton("Edit");
				orderEdit.setEnabled(false);

				JButton orderRemove = new JButton("Remove");
				orderRemove.setEnabled(false);

				utilityButtonPanel.add(orderAdd);
				utilityButtonPanel.add(orderEdit);
				utilityButtonPanel.add(orderRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
				repaint();
			}
		});

		foodMenu.add(dishSelection);
		foodMenu.add(ingredientSelection);
		foodMenu.add(orderSelection);

		return foodMenu;
	}

	public JMenu getDeliveryMenu() {

		JMenu deliveryMenu = new JMenu("Delivery");


		JMenuItem droneSelection = new JMenuItem(new AbstractAction("Drone") {
			public void actionPerformed(ActionEvent e) {

				removeAll();

				String [] columns = {"Name", "Speed", "Status"};
				String [][] data = new String[10000][3];
				Iterator iter = database.drones.iterator();

				for(int i = 0; i< database.drones.size(); i++){
					if(iter.hasNext()) {
						Drone current = (Drone)iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getSpeed().toString();
						data[i][2] = current.getStatus();
					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);

				JPanel utilityButtonPanel  = new JPanel();

				JButton droneAdd = new JButton("Add"){
					public void actionPerformed(ActionEvent e) {}
				};

				JButton droneEdit = new JButton("Edit");
				droneEdit.setEnabled(false);

				JButton droneRemove = new JButton("Remove"){
					public void actionPerformed(ActionEvent e) {}
				};


				utilityButtonPanel.add(droneAdd);
				utilityButtonPanel.add(droneEdit);
				utilityButtonPanel.add(droneRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
			}
		});
		JMenuItem postcodeSelection = new JMenuItem(new AbstractAction("Postcode") {
			public void actionPerformed(ActionEvent e) {

				removeAll();

				String [] columns = {"Name", "Distance"};
				String [][] data = new String[10000][2];
				Iterator iter = database.postcodes.iterator();

				for(int i = 0; i< database.postcodes.size(); i++){
					if(iter.hasNext()) {
						Postcode current = (Postcode)iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getDistance().toString();
					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);

				JPanel utilityButtonPanel  = new JPanel();

				JButton postcodeAdd = new JButton("Add"){
					public void actionPerformed(ActionEvent e) {}
				};

				JButton postcodeEdit = new JButton("Edit");
				postcodeEdit.setEnabled(false);

				JButton postcodeRemove = new JButton("Remove"){
					public void actionPerformed(ActionEvent e) {}
				};


				utilityButtonPanel.add(postcodeAdd);
				utilityButtonPanel.add(postcodeEdit);
				utilityButtonPanel.add(postcodeRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
			}
		});
		JMenuItem supplierSelection = new JMenuItem(new AbstractAction("Supplier") {
			public void actionPerformed(ActionEvent e) {

				removeAll();

				String [] columns = {"Name", "Postcode", "Distance"};
				String [][] data = new String[10000][3];
				Iterator iter = database.suppliers.iterator();

				for(int i = 0; i< database.suppliers.size(); i++){
					if(iter.hasNext()) {
						comp1206.sushi.common.Supplier current = (comp1206.sushi.common.Supplier) iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getPostcode().getName();
						data[i][2] = current.getDistance().toString();
					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);

				JPanel utilityButtonPanel  = new JPanel();

				JButton supplierAdd = new JButton("Add"){
					public void actionPerformed(ActionEvent e) {}
				};

				JButton supplierEdit = new JButton("Edit");
				supplierEdit.setEnabled(false);

				JButton supplierRemove = new JButton("Remove");
				supplierRemove.setEnabled(false);


				utilityButtonPanel.add(supplierAdd);
				utilityButtonPanel.add(supplierEdit);
				utilityButtonPanel.add(supplierRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
				repaint();
			}
		});

		deliveryMenu.add(droneSelection);
		deliveryMenu.add(postcodeSelection);
		deliveryMenu.add(supplierSelection);

		return deliveryMenu;
	}

	public JMenu getOtherMenu() {

		JMenu otherMenu = new JMenu("Other");


		JMenuItem staffSelection = new JMenuItem(new AbstractAction("Staff") {
			public void actionPerformed(ActionEvent e) {

				removeAll();

				String [] columns = {"Name", "Status", "Fatigue"};
				String [][] data = new String[10000][3];
				Iterator iter = database.staff.iterator();

				for(int i = 0; i< database.staff.size(); i++){
					if(iter.hasNext()) {
						Staff current = (Staff)iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getStatus();
						data[i][2] = current.getFatigue().toString();
					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);

				JPanel utilityButtonPanel  = new JPanel();

				JButton staffAdd = new JButton("Add"){
					public void actionPerformed(ActionEvent e) {}
				};

				JButton staffEdit = new JButton("Edit");
				staffEdit.setEnabled(false);

				JButton staffRemove = new JButton("Remove"){
					public void actionPerformed(ActionEvent e) {}
				};


				utilityButtonPanel.add(staffAdd);
				utilityButtonPanel.add(staffEdit);
				utilityButtonPanel.add(staffRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
			}
		});
		JMenuItem userSelection = new JMenuItem(new AbstractAction("User") {
			public void actionPerformed(ActionEvent e) {

				removeAll();

				String [] columns = {"Name", "Postcode"};
				String [][] data = new String[10000][2];
				Iterator iter = database.users.iterator();

				for(int i = 0; i< database.users.size(); i++){
					if(iter.hasNext()) {
						User current = (User)iter.next();
						data[i][0] = current.getName();
						data[i][1] = current.getPostcode().getName();
					}
				}

				JTable jt = new JTable(data, columns){
					public boolean isCellEditable(int data, int columns){
						return false;
					}
				};
				jt.setPreferredScrollableViewportSize(new Dimension(700, 450));
				jt.setFillsViewportHeight(true);

				JScrollPane jsp = new JScrollPane(jt);
				JPanel panel = new JPanel();
				panel.add(jsp);
				add(panel);

				JPanel utilityButtonPanel  = new JPanel();

				JButton userAdd = new JButton("Add");
				userAdd.setEnabled(false);

				JButton userEdit = new JButton("Edit");
				userEdit.setEnabled(false);

				JButton userRemove = new JButton("Remove");
				userRemove.setEnabled(false);

				utilityButtonPanel.add(userAdd);
				utilityButtonPanel.add(userEdit);
				utilityButtonPanel.add(userRemove);

				add(utilityButtonPanel, BorderLayout.SOUTH);

				revalidate();
				repaint();
			}
		});


		otherMenu.add(staffSelection);
		otherMenu.add(userSelection);

		return otherMenu;
	}
	
}
