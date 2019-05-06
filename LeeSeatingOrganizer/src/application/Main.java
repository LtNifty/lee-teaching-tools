package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
	// variables I thought were better suited as non-local
	private VBox brokenVBox = new VBox();
	private Stage brokenStage = new Stage();
	private Label botLbl = new Label();
	private TextField textPC = new TextField();
	private TextField textStu = new TextField();
	private List<Integer> ignore = new ArrayList<Integer>();

	@Override
	public void start(Stage primaryStage) {
		try {
			// declare && initialize all variables
			BorderPane border = new BorderPane();
			HBox hbox = new HBox();
			Label labelPC = new Label("# of\nPCs");
			Label labelStu = new Label("# of\nStudents");

			// Creates labels next to the
			// TextFields && aligns them
			labelPC.setTextAlignment(TextAlignment.CENTER);
			labelStu.setTextAlignment(TextAlignment.CENTER);

			// Sets the width for
			// the TextFields
			textPC.setPrefWidth(30);
			textStu.setPrefWidth(30);

			// Forces input to be an integer
			// for stupid end users
			textPC.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue,
						String newValue) {
					if (!newValue.matches("\\d*")) {
						textPC.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			textStu.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue,
						String newValue) {
					if (!newValue.matches("\\d*")) {
						textStu.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});

			// Allows the "Enter" key to
			// become a valid button press
			textPC.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					if (keyEvent.getCode() == KeyCode.ENTER)  {
						genList();
					}
				}
			});
			textStu.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					if (keyEvent.getCode() == KeyCode.ENTER)  {
						genList();
					}
				}
			});

			// Allows only two
			// characters for input
			textPC.setOnKeyTyped(event ->{
				if(textPC.getText().length() > 1) event.consume();
			});
			textStu.setOnKeyTyped(event ->{
				if(textStu.getText().length() > 1) event.consume();
			});

			// Puts all the nodes
			// into the HBox
			hbox.setPadding(new Insets(15, 12, 15, 12));
			hbox.setSpacing(10);
			hbox.getChildren().addAll(labelPC, textPC, labelStu, textStu);

			// Sets up the BorderPane
			// from top to bottom
			border.setTop(new Label("Welcome to Mr. Lee's Seating Assignment Assistant!"
					+ "\nInput the integer that corresponds to the description."
					+ "\nMade by: Sebastian Jankowski, APCS-A TI 2018 - 19"));
			border.setCenter(hbox);
			border.setRight(addRight());
			border.setBottom(botLbl);
			botLbl.setStyle("-fx-text-fill:red");

			brokenVBox.getChildren().add(new Label("Broken PC List"));
			brokenVBox.setPadding(new Insets(10,10,10,10));
			brokenVBox.setPrefHeight(300);

			// Sets up scene && primaryStage
			primaryStage.setTitle("Lee's Teacher Tools");
			primaryStage.setScene(new Scene(border));
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					brokenStage.close();
				}
			});

			brokenStage.setScene(new Scene(brokenVBox));
			brokenStage.setX(primaryStage.getX() + 375);
			brokenStage.setY(primaryStage.getY());
			brokenStage.show();
			brokenStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					event.consume();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private VBox addRight() {
		// declare && initialize all variables
		VBox vbox = new VBox();
		Button randButton = new Button("Randomize");
		Button pcButton = new Button("Broken Computer");

		// sets up "Broken Computer" button &&
		// its on click event
		pcButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent event) {
						// declare && initialize all variables
						Stage primaryStage = new Stage();
						BorderPane border = new BorderPane();
						VBox vbox = new VBox();
						TextField text = new TextField();
						Button b = new Button("Remove from List");
						Button b2 = new  Button("Return");
						Label lbl = new Label();

						// textfield setup
						text.setPrefWidth(2);
						text.textProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue,
									String newValue) {
								if (!newValue.matches("\\d*") || !newValue.matches(" ") || !newValue.matches("-")) {
									text.setText(newValue.replaceAll("[^\\d, , -]", ""));
								}
							}
						});
						text.setOnKeyPressed(
								new EventHandler<KeyEvent>() {
									@Override public void handle(KeyEvent keyEvent) {
										if (keyEvent.getCode() == KeyCode.ENTER)  {
											handlePC(lbl, text, primaryStage);
										}
									}
								});

						// setup for button
						// "Remove from List"
						b.setTextAlignment(TextAlignment.CENTER);
						b.setOnAction(
								new EventHandler<ActionEvent>() {
									@Override public void handle(ActionEvent event) {
										handlePC(lbl, text, primaryStage);
									}
								});

						b2.setTextAlignment(TextAlignment.CENTER);
						b2.setOnAction(
								new EventHandler<ActionEvent>() {
									@Override public void handle(ActionEvent event) {
										primaryStage.close();
									}
								});

						vbox.getChildren().addAll(b, b2);
						vbox.setAlignment(Pos.CENTER);
						vbox.setSpacing(8);

						// setup BorderPane
						border.setPadding(new Insets(10,10,10,10));
						border.setTop(new Label("Enter the seat number with the broken computer."
								+ "\nUse spaces to add multiple seat numbers."
								+ "\nUse negatives to remove PCs from the list."));
						border.setCenter(text);
						border.setBottom(lbl);
						border.setRight(vbox);
						lbl.setStyle("-fx-text-fill:red");

						// setup primaryStage
						primaryStage.setScene(new Scene(border));
						primaryStage.setTitle("Broken Computer");
						primaryStage.setResizable(false);
						primaryStage.show();
					}
				});

		// sets up "Random" button &&
		// its on click event
		randButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent event) {
						genList();
					}
				});

		// setup vbox
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.getChildren().addAll(pcButton, randButton);
		vbox.setAlignment(Pos.CENTER);
		return vbox;
	}
	private void handlePC(Label lbl, TextField text, Stage primaryStage) {
		Scanner scan = new Scanner(text.getText());
		boolean problem = false;
		int num = 0;
		while (scan.hasNext() && problem == false) {
			String str = scan.next();
			if (str.length() > 10) {
				problem = true;
				lbl.setText("ERROR: input is too large! (11 char limit)");
				break;
			}
			try {
				num = Integer.parseInt(str);
			} catch (Exception e) {
				problem = true;
				lbl.setText("ERROR: Input error. Likely from \"-\" signs.");
				break;
			}
			if (num > 99 || num == 0) {
				problem = true;
				lbl.setText("ERROR: " + num + " is out of range! (1 - 99)");
			}
			else if (num < 0) {
				if (this.ignore.contains((Integer) Math.abs(num)) == false) {
					problem = true;
					lbl.setText("ERROR: PC #" + Math.abs(num) + " is not listed as broken!");
				}
				else
					for (int i = 0; i < brokenVBox.getChildren().size(); i++) {
						Label l = (Label) brokenVBox.getChildren().get(i);
						for (int j = 0; j < ignore.size(); j++) {
							if (l.getText().equals("Broken PC List"))
								continue;
							if (Integer.parseInt(l.getText().substring(4)) == ignore.get(j)) {
								brokenVBox.getChildren().remove(i);
								ignore.remove((Object) Integer.parseInt(l.getText().substring(4)));
							}
						}
					}
			}
			else if (ignore.contains(num)) {
				problem = true;
				lbl.setText("ERROR: " + num + " is already accounted for!");
			}
			else {
				ignore.add(num);
				brokenVBox.getChildren().add(new Label("PC #" + num));
			}
		}

		if (problem == false) {
			scan.close();
			primaryStage.close();
		}
	}
	private boolean genList() {
		/* WHY BOOLEAN RETURN TYPE:
		 * This method was originally meant to be void. However.
		 * because try/catch statements do not stop the method from
		 * continuing, I had to make the method return something to
		 * stop it from generating an empty list. Thus, I made the
		 * method return booleans.
		 * "false" means it failed, "true" means it worked.
		 */

		// declare && initialize all variables
		int numOfPC = 0, numOfStu = 0;

		// checks to make sure the
		// textfields aren't empty
		try {
			numOfPC = Integer.parseInt(textPC.getText());
		} catch (Exception e) {
			botLbl.setText("ERROR: Please enter the number of PCs.");
			return false;
		}
		try {
			numOfStu = Integer.parseInt(textStu.getText());
		} catch (Exception e) {
			botLbl.setText("ERROR: Please enter the number of students.");
			return false;
		}

		// clears bottom label from
		// displaying previous errors
		botLbl.setText("");

		// checks that there are enough
		// computers for the students
		int sum = numOfPC - ignore.size();
		if (sum < numOfStu) {
			if (sum < 0)
				sum = 0;
			JOptionPane.showMessageDialog(null, "Not enough computers!"
					+ "\nBroken PCs: " + ignore.size()
					+ "\nWorking PCs: " + (sum)
					+ "\nNumber of Students: " + numOfStu , "The Thanos Dilemma", JOptionPane.ERROR_MESSAGE);
		}
		else if (numOfStu == 0) {
			botLbl.setText("Why are you using this if you have no students?");
			return false;
		}
		else {
			// declare && initialize all variables
			Stage primaryStage = new Stage();
			VBox vbox = new VBox();
			List<Integer> nums = IntStream.iterate(1,n -> n + 1).limit(numOfStu).boxed().collect(Collectors.toList());

			// randomizes the array "nums"
			Collections.shuffle(nums);

			// generates list; variable count
			// accounts for broken PCs
			int count = 0;
			for (int i = 0; i < numOfPC; i++) {
				if (ignore.contains(i+1))
					vbox.getChildren().add(new Label("Seat " + (i + 1) + ":\tBROKEN"));
				else if (count >= numOfStu)
					vbox.getChildren().add(new Label("Seat " + (i + 1) + ":\t---"));
				else {
					vbox.getChildren().add(new Label("Seat " + (i + 1) + ":\t#" + nums.get(count)));
					count++;
				}
			}

			// setups vbox, scene, && primaryStage
			vbox.setPadding(new Insets(10, 10, 10, 10));
			Scene scene = new Scene(vbox);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		return true;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
