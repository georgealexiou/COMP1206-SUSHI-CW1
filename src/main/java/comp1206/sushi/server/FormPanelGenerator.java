package comp1206.sushi.server;

import javax.swing.*;
import java.util.List;

public class FormPanelGenerator{

    private JPanel formPanel;
    private GroupLayout layout;
    private List<Object> objects;

    public FormPanelGenerator(List<Object> objects){
        formPanel = new JPanel();
        layout = new GroupLayout(formPanel);
        this.objects = objects;
        generate();
    }

    public FormPanelGenerator(){
        formPanel = new JPanel();
        layout = new GroupLayout(formPanel);
        generate();
    }


    public void checkObjectType() {

    }

    public void generate(){

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(new JLabel("Dish"))
                        .addComponent(new JLabel("Description"))
                        .addComponent(new JLabel("Price")))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(new JTextField())
                        .addComponent(new JTextField()))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(new JButton("Find"))
                        .addComponent(new JButton("Cancel")))
        );
    }

}
