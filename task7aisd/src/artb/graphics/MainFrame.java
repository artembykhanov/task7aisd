package artb.graphics;

import artb.logic.Logic;
import artb.graphics.additional.ManipulateModes;
import artb.graphics.additional.ObjectModes;
import artb.logic.graph.AdjMatrixGraph;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel graphPanel;

    private JCheckBox vertexModeCheckBox;
    private JCheckBox edgeModeCheckBox;
    private JCheckBox addModeCheckBox;
    private JCheckBox delModeCheckBox;

    private JTextField depthField;
    private JTextField answerField;

    private JButton getVerticesButton;
    private JButton clearButton;

    private JLabel weightLabel;
    private JLabel answerLabel;
    private JButton getEdgesButton;

    private final Canvas canvas = new Canvas();
    int depth;

    public MainFrame() {
        super();

        configureFrame();
        graphPanel.add(canvas, BorderLayout.CENTER);
        createButtonListeners();

        pack();
        setVisible(true);
    }

    private void configureFrame() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Таск №7 13");
        setMinimumSize(new Dimension(1500, 800));
        setResizable(false);
    }

    private void createButtonListeners() {
        edgeModeCheckBox.addActionListener(o -> {
            vertexModeCheckBox.setSelected(false);
            canvas.changeObjectMode(ObjectModes.EDGE);
        });

        vertexModeCheckBox.addActionListener(o -> {
            edgeModeCheckBox.setSelected(false);
            canvas.changeObjectMode(ObjectModes.VERTEX);
        });


        addModeCheckBox.addActionListener(o -> {
            delModeCheckBox.setSelected(false);
            canvas.changeManipulateMode(ManipulateModes.ADD);
        });

        delModeCheckBox.addActionListener(o -> {
            addModeCheckBox.setSelected(false);
            canvas.changeManipulateMode(ManipulateModes.DELETE);
        });

        getVerticesButton.addActionListener(o -> {
            int depth = Integer.parseInt(depthField.getText());
            AdjMatrixGraph graph = canvas.getGraph();
            if (depth <= 0) {
                answerField.setText("Глубина задана неверно");
            } else if (Logic.isGraphConnected(graph,depth)) {
                List<Integer> answer = Logic.findVertices(graph,depth);
                answerField.setText(String.valueOf(answer));
            }
            else{
                answerField.setText("Граф не удовлетворяет теории об N рукопожатий");
            }
        });

        getEdgesButton.addActionListener(o -> {
            int depth = Integer.parseInt(depthField.getText());
            AdjMatrixGraph graph = canvas.getGraph();
            if (depth <= 0) {
                answerField.setText("Глубина задана неверно");
            } else if (Logic.isGraphConnected(graph,depth)) {
                List<Logic.Edge> answer = Logic.findEdges(graph, depth);
                answerField.setText(String.valueOf(answer));
            }
         else{
             answerField.setText("Граф не удовлетворяет теории об N рукопожатий");
         }

        });

        clearButton.addActionListener(o -> canvas.clearAll());
    }
}
