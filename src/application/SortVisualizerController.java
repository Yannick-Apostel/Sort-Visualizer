package application;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;


public class SortVisualizerController {

    private static final int NUM_BARS = 10;
    private static final int MAX_HEIGHT = 300;
    private static final int WIDTH = 50;
    private static final int DELAY = 300;
    public static final int[] NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Label[] labels;

    @FXML
    private StackPane barContainer;
    @FXML
    private Label compareLabel;
    @FXML
    public Label lbl1;
    @FXML
    public Label lbl2;
    @FXML
    public Label lbl3;
    @FXML
    public Label lbl4;
    @FXML
    public Label lbl5;
    @FXML
    public Label lbl6;
    @FXML
    public Label lbl7;
    @FXML
    public Label lbl8;
    @FXML
    public Label lbl9;
    @FXML
    public Label lbl0;

    @FXML
    private Label movementsLabel;
    
    private Rectangle[] bars;
    private int[] values;
    private int[] copiedvalues;
    private int comparisons = 0;
    private int movements=0;

    @FXML
    public void initialize() {
        generateBars();
    }

    private void generateBars() { 
        barContainer.getChildren().clear();
        bars = new Rectangle[NUM_BARS];
        values = new int[NUM_BARS];
        copiedvalues = new int[NUM_BARS];
        comparisons = 0;
        movements =0;
        updateComparisonLabel();
        updateMovementsLabel();
        
        labels = new Label[] { lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl0 };
        Random random = new Random();
        for (int i = 0; i < NUM_BARS; i++) {
            values[i] = random.nextInt(MAX_HEIGHT) + 50;
            Rectangle bar = new Rectangle(WIDTH, values[i]);
            bar.setFill(Color.BLUE);
            bars[i] = bar;
            barContainer.getChildren().add(bar);
            bar.setTranslateX(i * (WIDTH + 5) - (NUM_BARS * WIDTH) / 2.0);
        }
        lbl1.setText(""+values[0]);
        lbl2.setText(""+values[1]);
        lbl3.setText(""+values[2]);
        lbl4.setText(""+values[3]);
        lbl5.setText(""+values[4]);
        lbl6.setText(""+values[5]);
        lbl7.setText(""+values[6]);
        lbl8.setText(""+values[7]);
        lbl9.setText(""+values[8]);
        lbl0.setText(""+values[9]);
        for(int i=0; i<values.length;i++) {
        	copiedvalues[i]=values[i];
        }
    }
    
    public void compare() {
    	for(int i=0; i<values.length;i++) {
    		values[i]=copiedvalues[i];
    		bars[i].setHeight(values[i]);
    	}
    	lbl1.setText(""+values[0]);
        lbl2.setText(""+values[1]);
        lbl3.setText(""+values[2]);
        lbl4.setText(""+values[3]);
        lbl5.setText(""+values[4]);
        lbl6.setText(""+values[5]);
        lbl7.setText(""+values[6]);
        lbl8.setText(""+values[7]);
        lbl9.setText(""+values[8]);
        lbl0.setText(""+values[9]);
        comparisons=0;
        movements=0;
        updateMovementsLabel();
        updateComparisonLabel();
    }

    @FXML
    private void startBubbleSort() {
        Timeline timeline = new Timeline();
        int n = values.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                final int index = j;
                int delay = DELAY * (j + i * (n - 1));

                timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(delay), e -> performBubbleSortStep(index))
                );
            }
        }

        timeline.setCycleCount(1);
        timeline.play();
    }

    private void performBubbleSortStep(int index) {
        comparisons++;
        updateComparisonLabel();
        highlightBarsComp(index, index + 1);

        if (values[index] > values[index + 1]) {
        	movements++;
            swap(index, index + 1);
        }

        resetBarColors(index, index + 1);
    }
    @FXML
    private void startBubbleSortBack() {
        Timeline timeline = new Timeline();
        int n = values.length;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                final int index = j;
                int delay = DELAY * (j + (n - 1 - i) * (n - 1));

                timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(delay), e -> performBubbleSortStepBack(index))
                );
            }
        }

        timeline.setCycleCount(1);
        timeline.play();
    }

    private void performBubbleSortStepBack(int index) {
        comparisons++;
        updateComparisonLabel();
        highlightBarsComp(index, index + 1);

        if (values[index] < values[index + 1]) {
            movements++;
            swap(index, index + 1);
        }

        resetBarColors(index, index + 1);
    }

    private void highlightBarsComp(int i, int j) {
        bars[i].setFill(Color.ORANGE);
        bars[j].setFill(Color.ORANGE);

        ScaleTransition scaleI = new ScaleTransition(Duration.millis(DELAY / 2), bars[i]);
        ScaleTransition scaleJ = new ScaleTransition(Duration.millis(DELAY / 2), bars[j]);

        scaleI.setToX(1.1);
        scaleI.setToY(1.1);
        scaleJ.setToX(1.1);
        scaleJ.setToY(1.1);

        scaleI.play();
        scaleJ.play();
    }
    private void highlightBarsMove(int i, int j) {
    	bars[i].setFill(Color.RED);
        bars[j].setFill(Color.RED);
        
        ScaleTransition scaleI = new ScaleTransition(Duration.millis(DELAY / 2), bars[i]);
        ScaleTransition scaleJ = new ScaleTransition(Duration.millis(DELAY / 2), bars[j]);

        scaleI.setToX(1.1);
        scaleI.setToY(1.1);
        scaleJ.setToX(1.1);
        scaleJ.setToY(1.1);

        scaleI.play();
        scaleJ.play();
    }
    private void resetBarColors(int i, int j) {
        PauseTransition pause = new PauseTransition(Duration.millis(DELAY / 2));
        pause.setOnFinished(e -> {
            bars[i].setFill(Color.BLUE);
            bars[j].setFill(Color.BLUE);

            bars[i].setScaleX(1.0);
            bars[i].setScaleY(1.0);
            bars[j].setScaleX(1.0);
            bars[j].setScaleY(1.0);
        });
        pause.play();
    }

    public void startInsertionSort() {
        int n = values.length;
        performOuterLoopInsertion(1, n);
    }

    private void performOuterLoopInsertion(int i, int n) {
        if (i < n) {
            if (values[i] < values[i - 1]) {
            	comparisons++;
            	updateComparisonLabel();
            	highlightBarsComp(i, i-1);
            	resetBarColors(i, i-1);
                final int minum = values[i];
                performInnerLoopInsertion(i, minum, i - 1);
            } else {
                performOuterLoopInsertion(i + 1, n);
            }
        } else {
            for (int k = 0; k < values.length; k++) {
                System.out.println(values[k]);
            }
        }
    }

    private void performInnerLoopInsertion(int j, int minum, int prevIndex) {
        if (j > 0 && values[prevIndex] > minum) {
            final int innerIndex = prevIndex;
            final int outerIndex = j;

            performStepInsertion(innerIndex, outerIndex);
            highlightBarsMove(outerIndex, innerIndex);
            values[outerIndex] = values[innerIndex];
            bars[outerIndex].setHeight(values[outerIndex]);
            Platform.runLater(() -> highlightBarsComp(innerIndex, outerIndex));
            bars[outerIndex].setHeight(values[outerIndex]);
            bars[innerIndex].setHeight(values[innerIndex]);
            labels[innerIndex].setText(String.valueOf(values[innerIndex]));
            labels[outerIndex].setText(String.valueOf(values[outerIndex]));
            resetBarColors(innerIndex, outerIndex);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e ->{
            	
            	performInnerLoopInsertion(j - 1, minum, innerIndex - 1);
            	resetBarColors(outerIndex,innerIndex);
            }
            		);
            pause.play();
            movements++;
            resetBarColors(outerIndex, innerIndex);
            updateMovementsLabel();
        } else {
        	
            final int posJ = j;
            values[posJ] = minum;
            bars[posJ].setHeight(values[posJ]);
            labels[posJ].setText(String.valueOf(values[posJ]));
            
            movements++;
            updateMovementsLabel();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> performOuterLoopInsertion(j + 1, values.length));
            pause.play();
            
        }
    }
    
    
    public void startInsertionSortBack() {
        int n = values.length;
        performOuterLoopInsertionBack(1, n);
    }

    private void performOuterLoopInsertionBack(int i, int n) {
        if (i < n) {
            if (values[i] > values[i - 1]) { // Vergleich umgekehrt
                comparisons++;
                highlightBarsComp(i,i-1);
                updateComparisonLabel();
                final int current = values[i];
                resetBarColors(i,i-1);
                performInnerLoopInsertionBack(i, current, i - 1);
            } else {
                performOuterLoopInsertionBack(i + 1, n);
            }
        } else {
            for (int k = 0; k < values.length; k++) {
                System.out.println(values[k]);
            }
        }
    }

    private void performInnerLoopInsertionBack(int j, int current, int prevIndex) {
        if (j > 0 && values[prevIndex] < current) { // Vergleich umgekehrt
            final int innerIndex = prevIndex;
            final int outerIndex = j;

            performStepInsertion(innerIndex, outerIndex);
            highlightBarsMove(outerIndex, innerIndex);
            values[outerIndex] = values[innerIndex];
            bars[outerIndex].setHeight(values[outerIndex]);
            Platform.runLater(() -> highlightBarsComp(innerIndex, outerIndex));
            bars[outerIndex].setHeight(values[outerIndex]);
            bars[innerIndex].setHeight(values[innerIndex]);
            labels[innerIndex].setText(String.valueOf(values[innerIndex]));
            labels[outerIndex].setText(String.valueOf(values[outerIndex]));
            resetBarColors(innerIndex, outerIndex);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
            	performInnerLoopInsertionBack(j - 1, current, innerIndex - 1);
            	resetBarColors(outerIndex,innerIndex);
            });
            pause.play();
            resetBarColors(outerIndex,innerIndex);
            movements++;
            updateMovementsLabel();
        } else {
            final int posJ = j;
            values[posJ] = current;
            bars[posJ].setHeight(values[posJ]);
            movements++;
            labels[posJ].setText(String.valueOf(values[posJ]));
            
            updateMovementsLabel();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> performOuterLoopInsertionBack(j + 1, values.length));
            pause.play();
        }
    }

   

    

    public void startSelectionSort() {
        startSelectionSortStep(0, 0);
    }

    private void startSelectionSortStep(int i, int j) {
        if (i < values.length) {
            if (j < values.length) {
                if (j == 0) {
                    j = i + 1;
                }

                int minPos = i;
                int minWert = values[i];

                for (int k = j; k < values.length; k++) {
                    if (values[k] < minWert) {
                    	highlightBarsComp(i,k);
                    	comparisons++;
                    	updateComparisonLabel();
                        minWert = values[k];
                        minPos = k;
                        resetBarColors(i,k);
                    }
                }
                
                int temp = values[i];
                values[i] = values[minPos];
                values[minPos] = temp;
                labels[i].setText(String.valueOf(values[i]));
                labels[minPos].setText(String.valueOf(values[minPos]));
                movements++;
                updateMovementsLabel();
                
                final int finalI = i;
                final int finalMinPos = minPos;
                Platform.runLater(() -> {
                	highlightBarsMove(i,finalMinPos);
                    bars[finalI].setHeight(values[finalI]);
                    bars[finalMinPos].setHeight(values[finalMinPos]);
                   
                    resetBarColors(finalI, finalMinPos);

                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(e -> startSelectionSortStep(i + 1, 0));
                    pause.play();
                });
            }
        } else {
            Platform.runLater(() -> {
                for (int k = 0; k < values.length; k++) {
                    System.out.println(values[k]);
                }
            });
        }
    }
    public void startSelectionSortBack() {
        startSelectionSortStepBack(0);
    }

    private void startSelectionSortStepBack(int i) {
        if (i < values.length - 1) { // Nur bis zum vorletzten Element
            int maxPos = i;
            int maxWert = values[i];

            for (int j = i + 1; j < values.length; j++) {
                if (values[j] > maxWert) { 
                	highlightBarsComp(i,j);// Vergleich umgekehrt
                    comparisons++;
                    updateComparisonLabel();
                    maxWert = values[j];
                    maxPos = j;
                    resetBarColors(i,j);
                }
            }

            int temp = values[i];
            values[i] = values[maxPos];
            values[maxPos] = temp;
            movements++;
            updateMovementsLabel();
            labels[i].setText(String.valueOf(values[i]));
            labels[maxPos].setText(String.valueOf(values[maxPos]));

            final int finalI = i;
            final int finalMaxPos = maxPos;
            Platform.runLater(() -> {
            	highlightBarsMove(finalI, finalMaxPos);
                bars[finalI].setHeight(values[finalI]);
                bars[finalMaxPos].setHeight(values[finalMaxPos]);
                
                resetBarColors(finalI, finalMaxPos);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> startSelectionSortStepBack(finalI + 1));
                pause.play();
            });
        } else {
            Platform.runLater(() -> {
                for (int k = 0; k < values.length; k++) {
                    System.out.println(values[k]);
                }
            });
        }
    }


    

    

    public void startQuickSort() throws InterruptedException {
        QuickSort(0, values.length-1);
    }
    
    public void QuickSort(int li, int re) {
        int i = li;
        int j = re;
        int pivot = values[(li + re) / 2];
        int temp;

        do {
            while (values[i] < pivot) {
            	
            	comparisons++;
            	updateComparisonLabel();
                i++;
              
            }
            	
            while (values[j] > pivot) {
            	
            	 j--;
            comparisons++;
           updateComparisonLabel();
          
            }
               
            if (i <= j) {
            	
                temp = values[i];
                values[i] = values[j];
                values[j] = temp;
                movements++;
                updateMovementsLabel();
                labels[i].setText(String.valueOf(values[i]));
                labels[j].setText(String.valueOf(values[j]));
               
                final int finalI = i;
                final int finalJ = j;

                
                bars[finalI].setFill(Color.RED);
            	bars[finalJ].setFill(Color.RED);
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                    	
                        Platform.runLater(() -> bars[finalI].setHeight(values[finalI]));
                        
                    }),
                    new KeyFrame(Duration.seconds(2), e -> {
                        Platform.runLater(() -> bars[finalJ].setHeight(values[finalJ]));
                    })
                );

                resetBarColors(finalI, finalJ);
                
                timeline.setCycleCount(1);
                timeline.play();

                
                i++;
                j--;
            }
        } while (i <= j);

       
        if (li < j) {
            
            final int finalLi = li;
            final int finalJ = j;
            Timeline nextStepLeft = new Timeline(new KeyFrame(Duration.seconds(1), e -> QuickSort(finalLi, finalJ)));
            nextStepLeft.setCycleCount(1);
            nextStepLeft.play();
        }

        if (i < re) {
           
            final int finalI = i;
            final int finalRe = re;
            Timeline nextStepRight = new Timeline(new KeyFrame(Duration.seconds(1), e -> QuickSort(finalI, finalRe)));
            nextStepRight.setCycleCount(1);
            nextStepRight.play();
        }

        
        
            for (int a = 0; a < values.length; a++) {
                System.out.println(values[a]);
            }
        
    }
    public void startQuickSortBack() throws InterruptedException {
        QuickSortBack(0, values.length - 1);
    }

    public void QuickSortBack(int li, int re) {
        int i = li;
        int j = re;
        int pivot = values[(li + re) / 2];
        int temp;

        do {
            while (values[i] > pivot) { 
                comparisons++;
                updateComparisonLabel();
                i++;
            }

            while (values[j] < pivot) { 
                j--;
                comparisons++;
                updateComparisonLabel();
            }

            if (i <= j) {
                temp = values[i];
                values[i] = values[j];
                values[j] = temp;
                movements++;
                updateMovementsLabel();
                labels[i].setText(String.valueOf(values[i]));
                labels[j].setText(String.valueOf(values[j]));
                
                final int finalI = i;
                final int finalJ = j;

                
                bars[finalI].setFill(Color.RED);
                bars[finalJ].setFill(Color.RED);
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        Platform.runLater(() -> bars[finalI].setHeight(values[finalI]));
                    }),
                    new KeyFrame(Duration.seconds(2), e -> {
                        Platform.runLater(() -> bars[finalJ].setHeight(values[finalJ]));
                    })
                );

                resetBarColors(finalI, finalJ);

                timeline.setCycleCount(1);
                timeline.play();

              
                i++;
                j--;
            }
        } while (i <= j);

       
        if (li < j) {
            
            final int finalLi = li;
            final int finalJ = j;
            Timeline nextStepLeft = new Timeline(new KeyFrame(Duration.seconds(2), e -> QuickSortBack(finalLi, finalJ)));
            nextStepLeft.setCycleCount(1);
            nextStepLeft.play();
        }

        if (i < re) {
            
            final int finalI = i;
            final int finalRe = re;
            Timeline nextStepRight = new Timeline(new KeyFrame(Duration.seconds(2), e -> QuickSortBack(finalI, finalRe)));
            nextStepRight.setCycleCount(1);
            nextStepRight.play();
        }

        
        
            for (int a = 0; a < values.length; a++) {
                System.out.println(values[a]);
            }
       
    }


    


    
    private void performStepInsertion(int innerIndex, int outerIndex) {
        comparisons++;
        updateComparisonLabel();
        highlightBarsComp(innerIndex, outerIndex);
        resetBarColors(innerIndex, outerIndex);
    }


    

    private void updateComparisonLabel() {
        compareLabel.setText("Vergleiche: " + comparisons);
    }
    private void updateMovementsLabel() {
        movementsLabel.setText("Movements: " + movements);
    }

    private void swap(int i, int j) {
    	updateMovementsLabel();
        bars[i].setFill(Color.RED);
        bars[j].setFill(Color.RED);
        int temp = values[i];
        values[i] = values[j];
        values[j] = temp;

        labels[i].setText(String.valueOf(values[i]));
        labels[j].setText(String.valueOf(values[j]));
        bars[i].setHeight(values[i]);
        bars[j].setHeight(values[j]);
    }

    @FXML
    private void resetArray() {
        generateBars();
    }
}
