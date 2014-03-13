import javax.swing.*;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetrisblock extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int blockType;
	private int turnState;
	private int score = 0;
	private int nextBlockType = -1, nextTurnState = -1;
	private int x, y; // 当前方块位置
	private Timer timer;

	/**
	 * 地图
	 **/
	private int map[][] = new int[12][21];

	/**
	 * 方块类型
	 **/
	private final int[][][] shapes = new int[][][] {
			// 长条I型
			{ { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
					{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
			// 倒Z型
			{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
			// Z型
			{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
			// J型
			{ { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, },
			// L型
			{ { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, },
			// 田型
			{ { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// 土型
			{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } } };

	Tetrisblock() {
		newMap();
		drawWall();
		newBlock();
		//addKeyListener(this);
		//setFocusable(true);
		timer = new Timer(500, new TimerListener());
		timer.start();
	}

	private void newBlock() {
		if (nextBlockType == -1 && nextTurnState == -1) {
			blockType = (int) (Math.random() * 1000) % 7;
			turnState = (int) (Math.random() * 1000) % 4;
			nextBlockType = (int) (Math.random() * 1000) % 7;
			nextTurnState = (int) (Math.random() * 1000) % 4;
	
		} else {
			blockType = nextBlockType;
			turnState = nextTurnState;
			nextBlockType = (int) (Math.random() * 1000) % 7;
			nextTurnState = (int) (Math.random() * 1000) % 4;
		}
	
		x = 4;
		y = 0;
	
		if (gameOver(x, y) == 1) {
			newMap();
			drawWall();
			score = 0;
			JOptionPane.showMessageDialog(null, "GAMEOVER");
		}
	
	}

	private void newMap() {
		int i, j;
		for (i = 0; i < 12; i++) {
			for (j = 0; j < 21; j++) {
				map[i][j] = 0;
			}
		}
	}

	private void drawWall() {
		int i, j;
		for (i = 0; i < 12; i++) {
			map[i][20] = 2;
		}
	
		for (j = 0; j < 21; j++) {
			map[0][j] = map[11][j] = 2;
		}
	}

	private void turn() {
		int tempTurnState = turnState + 1;
		tempTurnState = tempTurnState % 4;
		if (blow(x, y, blockType, tempTurnState) == 1) {
			turnState = tempTurnState;
		}

		repaint();
	}

	private void left() {
		if (blow(x - 1, y, blockType, turnState) == 1) {
			x--;
		}

		repaint();
	}

	private void right() {
		if (blow(x + 1, y, blockType, turnState) == 1) {
			x++;
		}

		repaint();
	}

	private void down() {
		if (blow(x, y + 1, blockType, turnState) == 1) {
			y++;
		} else {
			add(x, y, blockType, turnState);
			delLine();
			newBlock();
		}

		repaint();
	}

	private void delLine() {
		int c = 0;
		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 12; j++)
				if (map[j][i] == 1) {
					c++;
					if (10 == c) {
						score += 10;
						for (int m = i; m > 0; m--) {
							for (int n = 0; n < 12; n++) {
								map[n][m] = map[n][m - 1];
							}
						}
					}
				}
			c = 0;
		}
	}

	private int gameOver(int x, int y) {
		if (blow(x, y, blockType, turnState) == 0) {
			return 1;
		}
		return 0;
	}

	private void add(int x, int y, int blockType, int turnState) {
		int yPos = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (shapes[blockType][turnState][yPos] == 1) {
					map[x + 1 + j][y + i] = shapes[blockType][turnState][yPos];
				}
				yPos++;
			}
		}
	}

	private int blow(int x, int y, int blockType, int turnState) {
		int i, j;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (((shapes[blockType][turnState][i * 4 + j] == 1) && (map[x
						+ 1 + j][y + i] == 1))
						|| ((shapes[blockType][turnState][i * 4 + j] == 1) && (map[x
								+ 1 + j][y + i] == 2))) {
					return 0;
				}
			}
		}
		return 1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			down();
			break;
		case KeyEvent.VK_UP:
			turn();
			break;
		case KeyEvent.VK_RIGHT:
			right();
			break;
		case KeyEvent.VK_LEFT:
			left();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void paint(Graphics g) {
		super.paint(g);

		int i, j;
		// 画当前方块
		for (j = 0; j < 16; j++) {
			if (shapes[blockType][turnState][j] == 1) {
				g.fillRect((j % 4 + x + 1) * 15, (j / 4 + y) * 15, 15, 15);
			}
		}

		for (i = 0; i < 12; i++) {
			for (j = 0; j < 21; j++) {
				if (map[i][j] == 1) {
					g.fillRect(i * 15, j * 15, 15, 15);
				}
				if (map[i][j] == 2) {
					g.drawRect(i * 15, j * 15, 15, 15);
				}
			}
		}

		g.drawString("score = " + score, 225, 15);
		g.drawString("下一方块形状", 225, 50);

		for (j = 0; j < 16; j++) {
			if (shapes[nextBlockType][nextTurnState][j] == 1) {
				g.fillRect(225 + (j % 4) * 15, (j / 4) * 15 + 100, 15, 15);
			}
		}
	}

	public void newGame() {
		newBlock();
		newMap();
		drawWall();
	}
	
	public void pauseGame() {
		timer.stop();
	}
	
	public void continueGame() {
		timer.start();
	}
	class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			down();
		}
	}
}
