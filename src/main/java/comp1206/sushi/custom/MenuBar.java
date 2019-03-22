package comp1206.sushi.custom;

import comp1206.sushi.server.ServerWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        add(getFoodMenu());
        add(getDeliveryMenu());
        add(getOtherMenu());
    }

    public JMenu getFoodMenu() {

        JMenu foodMenu = new JMenu("Food");


        JMenuItem dishSelection = new JMenuItem(new AbstractAction("Dish") {
            public void actionPerformed(ActionEvent e) {

            }
        });
        JMenuItem ingredientSelection = new JMenuItem(new AbstractAction("Ingredient") {
            public void actionPerformed(ActionEvent e) {
            }
        });
        JMenuItem orderSelection = new JMenuItem(new AbstractAction("Order") {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        JMenuItem postcodeSelection = new JMenuItem(new AbstractAction("Postcode") {
            public void actionPerformed(ActionEvent e) {
            }
        });
        JMenuItem supplierSelection = new JMenuItem(new AbstractAction("Supplier") {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        JMenuItem userSelection = new JMenuItem(new AbstractAction("User") {
            public void actionPerformed(ActionEvent e) {
            }
        });


        otherMenu.add(staffSelection);
        otherMenu.add(userSelection);

        return otherMenu;
    }
}
