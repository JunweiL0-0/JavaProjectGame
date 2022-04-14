package main.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;

import main.java.controller.GameController;
import main.java.model.Monster;


/**
 * ChooseMonsterScreen. This screen will be shown after the user clicked the "Confirm" button on the LandingScreen.
 */
public class ChooseMonsterScreen {
	private GameController gc;
	private ArrayList<Monster> availableMonsters;
	private ArrayList<Monster> selectedMonsters;
	private ArrayList<JToggleButton> monsterButtons;
	// swing components
	private JFrame chooseFrame;
	private JButton confirmButton;
	private JLabel hintMessageLabel;


	/**
	 * Constructor for ChooseMonsterScreen.
	 *
	 * @param gc gameController
	 */
	public ChooseMonsterScreen(GameController gc) {
		this.gc = gc;
		this.availableMonsters = new ArrayList<>();
		this.selectedMonsters = new ArrayList<>();
		this.monsterButtons = new ArrayList<>();
		// get initMonsters
		this.availableMonsters = getInitMonsters();

		initialize();
		show(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// *******************************************************************
		// *                      CHOOSE YOUR MONSTER!                       * --> Title
		// *     ---------- ---------- ---------- ---------- ----------      * -
		// *     |MONSTER1| |MONSTER2| |MONSTER3| |MONSTER4| |MONSTER5|      *  \
		// *     ---------- ---------- ---------- ---------- ----------      *   \
		// *     ---------- ---------- ---------- ---------- ----------      *   / ButtonDetailPanel
		// *     | DETAIL | | DETAIL | | DETAIL | | DETAIL | | DETAIL |      *  /
		// *     ---------- ---------- ---------- ---------- ----------      * -
		// *               Hint Message (Validate Selections)                *
		// *                       ----------------                          *
		// *                       |   CONFIRM    |                          *
		// *                       ----------------                          *
		// *******************************************************************
		// add components to chooseFrame
		this.chooseFrame = getChooseFrame();
		this.chooseFrame.getContentPane().add(getTitle());
		this.chooseFrame.getContentPane().add(getButtonDetailPanel());
		// store the hintMessageLabel into a variable
		this.hintMessageLabel = getHintMessageLabel();
		this.chooseFrame.getContentPane().add(this.hintMessageLabel);
		// store the confirmButton into a variable
		this.confirmButton = getConfirmButton();
		this.chooseFrame.getContentPane().add(this.confirmButton);
	}

//
//	private boolean isMaxSelections() {
//		return this.availableMonsters.size() == 3;
//	}
//
//	private void addMonsterToList(int selectedMonsterId) {
//		this.selectedMonsters.add(this.availableMonsters.get(selectedMonsterId));
//	}

	public void show(Boolean visible) {
		this.chooseFrame.setVisible(visible);
	}

	private void closeAndDestroyCurrentScreen() {
		show(false);
		this.chooseFrame.dispose();
	}

	private void disableConfirmButton() {
		this.confirmButton.setEnabled(false);
	}

	private void enableConfirmButton() {
		this.confirmButton.setEnabled(true);
	}

	private void validateSelection() {
		int counter = 0;
		// loop through the list and count selected button
		for (JToggleButton b: this.monsterButtons) {
			if (b.isSelected()) {
				counter++;
			}
		}
		// enable/disable confirmButton
		if (counter <= 0) {
			disableConfirmButton();
			setHintMessageLabel("Select a monster!");
		} else if (counter <= 3) {
			enableConfirmButton();
			setHintMessageLabel("Cool! You are ready to fight!");
		} else {
			disableConfirmButton();
			setHintMessageLabel("?? No, maximum three monsters!");
		}
	}

	private void setHintMessageLabel(String hint) {
		this.hintMessageLabel.setText(hint);
	}

	private String constructMonsterDetail(Monster monster) {
		return String.format("MaxHealth: %d\nDamage: %d\nLevel: %d\n",
				monster.getMaxHealth(), monster.getDamage(), monster.getLevel());
	}

	/*
	Swing components
	 */
	/**
	 * Return a new chooseFrame
	 *
	 * @return return a new chooseFrame
	 */
	private JFrame getChooseFrame() {
		JFrame chooseFrame = new JFrame();
		chooseFrame.setBounds(100, 100, 800, 500);
		chooseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chooseFrame.setLocationRelativeTo(null);
		chooseFrame.getContentPane().setBackground(Color.black);
		chooseFrame.setLayout(null);
		chooseFrame.setResizable(false);

		return chooseFrame;
	}

	/**
	 * Return a title for ChooseFrame
	 *
	 * @return a title
	 */
	private JLabel getTitle() {
		JLabel title = new JLabel("CHOOSE YOUR MONSTER(S)!",SwingConstants.CENTER);
		title.setBounds(20,20,760,120);
		title.setFont(new Font("Serif", Font.PLAIN, 55));
		title.setBackground(Color.black);
		title.setForeground(Color.white);
		return title;
	}

	/**
	 * Return a buttonPanel which contains monsterButtons and monsterDetails.
	 *
	 * @return a buttonPanel which contains monsterButtons and monsterDetails.
	 */
	private JPanel getButtonDetailPanel() {
		// create panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setBounds(20,140,760,160);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		// add buttons to the panel one by one
		for (int indexInList=0; indexInList<this.availableMonsters.size(); indexInList++) {
			JToggleButton button = getMonsterButton(indexInList);
			monsterButtons.add(button);
			panel.add(button);
		}
		// add details to the panel one by one
		for (int indexInList=0; indexInList<this.availableMonsters.size(); indexInList++) {
			panel.add(getMonsterDetail(indexInList));
		}

		return panel;
	}

	/**
	 * This function will generate a monsterButton for a specific monster in the availableMonsters list.
	 * The button will have the monster's name as its text.
	 *
	 * @param indexInList an index used to locate a specific monster in the availableMonsters list.
	 * @return a monsterButton
	 */
	private JToggleButton getMonsterButton(int indexInList) {
		Monster monster = this.availableMonsters.get(indexInList);
		JToggleButton button = new JToggleButton();
		button.setText(monster.getName());
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		button.setPreferredSize(new Dimension(144,100));
		button.addActionListener(actionEvent -> validateSelection());
		return button;
	}

	/**
	 * Generate a monster's detail(JTextArea) by using the indexInList.
	 *
	 * @param indexInList an index used to locate a specific monster in the availableMonsters list.
	 * @return a detail(JTextArea) for a single monster
	 */
	private JTextArea getMonsterDetail(int indexInList) {
		Monster monster = this.availableMonsters.get(indexInList);
		JTextArea detail = new JTextArea();
		detail.setPreferredSize(new Dimension(144,100));
//		detail.setBounds(0,370,800,150);
		detail.setForeground(Color.white);
		detail.setBackground(Color.black);
		detail.setBorder(null);
		detail.setText(constructMonsterDetail(monster));
		return detail;
	}

	private JLabel getHintMessageLabel() {
		// create label
		JLabel diffLabel = new JLabel("", SwingConstants.LEFT);
		diffLabel.setBounds(200,350,400,30);
		diffLabel.setFont(new Font("Serif", Font.PLAIN, 13));
		diffLabel.setBackground(Color.black);
		diffLabel.setForeground(Color.red);

		return diffLabel;
	}

	/**
	 * Return the confirmButton
	 *
	 * @return a confirmButton
	 */
	private JButton getConfirmButton() {
		// create button
		JButton newConfirmButton = new JButton();
		newConfirmButton.setBounds(200, 380, 400, 50);
		// setText via html so that we can see the text even the button is being disabled
		newConfirmButton.setText("<html><p style=\"color:red;font-size:20\">CONFIRM</p></html>");
		// as the player haven't selected anything. We disable the confirm button.
		newConfirmButton.setEnabled(false);
		// confirmButton listener
		addConfirmButtonListener(newConfirmButton);

		return newConfirmButton;
	}

	/*
	Listeners go here
	 */
	/**
	 * Add an actionListener to the button.
	 */
	private void addConfirmButtonListener(JButton button) {
		button.addActionListener(actionEvent -> switchToGameScreen());
	}

	/*
	Functions used to interact with gameController
	 */
	/**
	 * Get initMonsters from the gameController.
	 * @return iniMonsters
	 */
	private ArrayList<Monster> getInitMonsters() {
		return this.gc.getInitMonsters();
	}

	private void switchToGameScreen() {
		this.gc.launchLandingScreen();
		closeAndDestroyCurrentScreen();
	}
}
