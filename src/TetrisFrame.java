import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class TetrisFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static JMenu game = new JMenu("游戏");
	JMenuItem newGame = new JMenuItem("新游戏");
	JMenuItem pause = new JMenuItem("暂停");
	JMenuItem goon = new JMenuItem("继续");
	JMenuItem exit = new JMenuItem("退出");

	static JMenu help = new JMenu("帮助");
	JMenuItem about = new JMenuItem("关于");
	
	JMenuBar menu = new JMenuBar();

	Tetrisblock tetris = new Tetrisblock();
	public TetrisFrame() {
		addKeyListener(tetris);
		add(tetris);
		
		setJMenuBar(menu);
		
		menu.add(game);
		menu.add(help);
		
		game.add(newGame);
		game.add(pause);
		game.add(goon);
		game.add(exit);
		
		help.add(about);
						
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 375);
		setTitle("俄罗斯方块");
		setVisible(true);
		setResizable(true);
		
		newGame.addActionListener(this);
		pause.addActionListener(this);
		goon.addActionListener(this);
		exit.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TetrisFrame f = new TetrisFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newGame) {
			tetris.newGame();
		} else if (e.getSource() == pause) {
			tetris.pauseGame();
		} else if (e.getSource() == goon) {
			tetris.continueGame();
		} else if (e.getSource() == about) {
			DispalyToast("左右键移动,向上键选择");
		} else if (e.getSource() == exit) {
			System.exit(0);
		}
	}

	public void DispalyToast(String str) {
		JOptionPane.showMessageDialog(null, str, "提示",
				JOptionPane.ERROR_MESSAGE);
	}
}
