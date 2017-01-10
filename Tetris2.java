package Tetris2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Tetris2 {
	JFrame window = new JFrame("Tetris");
	static ArrayList<ArrayList<Integer>> gamestate = new ArrayList<ArrayList<Integer>>();
	Random blockchooser = new Random();
	tetgrid screen = new tetgrid();
	int blocktype;
	int score=0;
	int gamescreen=1;
	
	public static void main(String[] args) {
		for (int x=0; x<=20; x++)gamestate.add(new ArrayList<Integer>());
		for (int x=0; x<=20; x++){
			for (int y=0; y<=9; y++){
				if (x==20)gamestate.get(x).add(1);
				else gamestate.get(x).add(0);
			}
		}		
		new Tetris2().go();
	}
	
	void go() { // making the initial window and implementing a KeyListener
		window.setSize(507,735);
		//game space is (100-400)x(50,650)
		//30x30 sized squares
		window.setTitle("Tetris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.add(screen);
		window.addKeyListener(new keyevents());
		blockspawn();
		}

	private class tetgrid extends JComponent {
		public void paintComponent(Graphics g){	
			Graphics2D grap = (Graphics2D) g;
			grap.setColor(Color.BLACK);
			grap.fillRect(0, 0, 1000,1000);//setting up black background
			if (gamescreen==1){
			grap.setColor(Color.WHITE);
			for (int x=100; x<=400; x+=30){//setting up vertical grid lines
				grap.drawLine(x, 50, x, 650);}
			for (int y=50; y<=650; y+=30){//setting up horizontal grid lines
				grap.drawLine(100, y, 400, y);}
			grap.setFont(new Font("Arial Black", Font.BOLD, 30));
			grap.drawString("Score: "+Integer.toString(score),140, 40);
			for (int x=0; x<20;x++){
				for (int y=0; y<10; y++){
					if (gamestate.get(x).get(y)>=1)grap.fillRect(100+(y*30), (50+(x*30)), 30, 30);
				}//game space is (100-400)x(50,650)
			}
			grap.setFont(new Font("Arial Black", Font.BOLD, 15));
			grap.drawString("Up to rotate, Down to place, Left and Right to move", 10, 680);
			}
			else if (gamescreen==2){//Game Over screen
				//game space is (100-400)x(50,650)
				grap.setColor(Color.WHITE);	
				for (int x=0; x<=4; x++){
					grap.fillRect(20, 50+(20*x), 20, 20);
					grap.fillRect(140, 50+(20*x), 20, 20);
					grap.fillRect(220, 50+(20*x), 20, 20);
					grap.fillRect(260, 50+(20*x), 20, 20);
					grap.fillRect(340, 50+(20*x), 20, 20);
					grap.fillRect(380, 50+(20*x), 20, 20);
					
					grap.fillRect(20, 200+(20*x), 20, 20);
					grap.fillRect(100, 200+(20*x), 20, 20);
					grap.fillRect(140, 200+(20*x), 20, 20);
					grap.fillRect(220, 200+(20*x), 20, 20);
					grap.fillRect(260, 200+(20*x), 20, 20);
					grap.fillRect(380, 200+(20*x), 20, 20);
				}
				for (int x=1; x<=4; x++){
					grap.fillRect(20+(20*x), 50, 20, 20);
					grap.fillRect(20+(20*x), 130, 20, 20);
					grap.fillRect(140+(20*x), 50, 20, 20);
					grap.fillRect(140+(20*x), 90, 20, 20);
					grap.fillRect(380+(20*x), 50, 20, 20);
					grap.fillRect(380+(20*x), 90, 20, 20);
					grap.fillRect(380+(20*x), 130, 20, 20);
					
					grap.fillRect(20+(20*x), 200, 20, 20);
					grap.fillRect(20+(20*x), 280, 20, 20);
					grap.fillRect(120+(20*x), 280, 20, 20);
					grap.fillRect(260+(20*x), 200, 20, 20);
					grap.fillRect(260+(20*x), 240, 20, 20);
					grap.fillRect(260+(20*x), 280, 20, 20);
					grap.fillRect(380+(20*x), 200, 20, 20);
					grap.fillRect(380+(20*x), 240, 20, 20);
				}
				grap.fillRect(460, 220, 20, 20);
				grap.fillRect(440, 260, 20, 20);
				grap.fillRect(460, 280, 20, 20);
				for (int x=0; x<=1; x++){
					grap.fillRect(80+(20*x), 90, 20, 20);
					grap.fillRect(100, 90+(20*x), 20, 20);
					grap.fillRect(280, 70+(20*x), 20, 20);
					grap.fillRect(300, 90+(20*x), 20, 20);
					grap.fillRect(320, 70+(20*x), 20, 20);
				}
				grap.setFont(new Font("Arial Black", Font.BOLD, 30));
				grap.drawString("Score: "+Integer.toString(score), 140, 380);
				}
			}
		}
		
	private void moveright(){// Shifts all the blocks of the active piece one block to the right
		for (int x=0; x<20; x++){
			for (int y=0; y<10; y++){
				if (gamestate.get(x).get(9-y)>=2){
					gamestate.get(x).set(10-y, gamestate.get(x).get(9-y));
					gamestate.get(x).set(9-y,0);
					window.repaint();}}}
	}
	
	private void moveleft(){// Shifts all the blocks of the active piece one block to the left
		for (int x=0; x<20; x++){
			for (int y=0; y<10; y++){
				if (gamestate.get(x).get(y)>=2){
					gamestate.get(x).set(y-1,gamestate.get(x).get(y));
					gamestate.get(x).set(y, 0);
					window.repaint();}}}}
	
	private class keyevents implements KeyListener{
		public void keyPressed(KeyEvent event) {// Accepts button presses from the user
			// and implements various functions according to the button pressed
			if (!blocked("right", 2)&&event.getKeyCode()==KeyEvent.VK_RIGHT)moveright();
			else if (!blocked("left", 2)&&event.getKeyCode()==KeyEvent.VK_LEFT)moveleft();
			else if (event.getKeyCode()==KeyEvent.VK_DOWN){finishdrop(instantblockdrop());
					window.repaint();}
			else if (event.getKeyCode()==KeyEvent.VK_UP&&blocktype!=5){
				for (int x=0; x<20; x++){
					for (int y=0; y<10; y++){
						if (gamestate.get(x).get(y)==3&&blocktype==0&&x>=1){rotate(x, y);
						break;}
						
						else if (gamestate.get(x).get(y)==3&&blocktype!=0&&x>=1){rotate(x,y);
						break;
					}
				}
			}
		}
			}
		public void keyReleased(KeyEvent event) {}
		public void keyTyped(KeyEvent event) {}
	}
	
	private void rotate(int xcoord, int ycoord){//makes adjustmenets to the active piece as
		// needed and rotates it. For example if a piece is right up against the edge of the
		// grid this function might shift it a square to the left and rotate it
		if (blocktype==0&&(blocked("left",3))){
			if (!blocked("right", 3)){
				moveright();
				if (canrotate(xcoord, ycoord+1)&&gamestate.get(xcoord-2).get(ycoord+1)!=2){actuallymove(xcoord, ycoord+1);}
				else if (!canrotate(xcoord, ycoord+1)&&gamestate.get(xcoord-2).get(ycoord+1)!=2)moveleft();
				else{
				if (!blocked("right",2)){
					moveright();
					if (canrotate(xcoord, ycoord+2))actuallymove(xcoord, ycoord+2);
					else {moveleft();
						  moveleft();}
					}
				}
			}
		}
		
		else if (blocktype==0&&(blocked("right",3))){
			if (!blocked("left", 2)){
				moveleft();
				if (canrotate(xcoord, ycoord-1)&&gamestate.get(xcoord+2).get(ycoord-1)!=2){actuallymove(xcoord, ycoord-1);}
				else if (!canrotate(xcoord, ycoord-1)&&gamestate.get(xcoord+2).get(ycoord-1)!=2)moveright();
				else{
				if (!blocked("left",2)){
					moveleft();
					if (canrotate(xcoord, ycoord-2))actuallymove(xcoord, ycoord-2);
					else {moveright();
						  moveright();}
					}
				}
				}
			}
		
		else if (ycoord==8&&blocktype==0&&gamestate.get(xcoord+2).get(ycoord)==2){
			if (!blocked("left",2)){
				moveleft();
				if (canrotate(xcoord, ycoord-1)){actuallymove(xcoord, ycoord-1);}
				else moveright();
			}
		}
		
		else if (ycoord==1&&blocktype==0&&gamestate.get(xcoord-2).get(ycoord)==2){
			if (!blocked("right",2)){
				moveright();
				if (canrotate(xcoord, ycoord+1)){actuallymove(xcoord, ycoord+1);}
				else moveleft();
			}
		}
		
		else if (blocked("right",3)){
			if (!blocked("left", 2)){
				moveleft();
				if (canrotate(xcoord, ycoord-1))actuallymove(xcoord, ycoord-1);
				else moveright();}
		}
		else if (blocked("left",3)){
			if (!blocked("right", 2)){
				moveright();
				if (canrotate(xcoord, ycoord+1))actuallymove(xcoord, ycoord+1);
				else moveleft();}
		}
		else if (canrotate(xcoord, ycoord)){
			actuallymove(xcoord, ycoord);}}
		
	private void actuallymove(int xcoord, int ycoord){// if canrotate is true, this function
		// carries out the actual movement on the grid and repaints it.
		
		if (gamestate.get(xcoord-1).get(ycoord)==2){
			gamestate.get(xcoord-1).set(ycoord,0);
			gamestate.get(xcoord).set(ycoord-1,gamestate.get(xcoord).get(ycoord-1)+3);}
		
		if (gamestate.get(xcoord).get(ycoord+1)==2){
			gamestate.get(xcoord).set(ycoord+1,0);
			gamestate.get(xcoord-1).set(ycoord,2);}
		
		if (gamestate.get(xcoord+1).get(ycoord)==2){
			gamestate.get(xcoord+1).set(ycoord,0);
			gamestate.get(xcoord).set(ycoord+1,2);}
		
		if (gamestate.get(xcoord).get(ycoord-1)==2){
			gamestate.get(xcoord).set(ycoord-1,0);
			gamestate.get(xcoord+1).set(ycoord,2);}
		
		if (gamestate.get(xcoord).get(ycoord-1)==5){
			gamestate.get(xcoord).set(ycoord-1,2);
			gamestate.get(xcoord+1).set(ycoord,2);}

		if (gamestate.get(xcoord).get(ycoord-1)==3){
			gamestate.get(xcoord).set(ycoord-1,2);}
		
		if (blocktype!=0){	

		//diagonals
		if (gamestate.get(xcoord-1).get(ycoord+1)==2){
			gamestate.get(xcoord-1).set(ycoord+1,0);
			gamestate.get(xcoord-1).set(ycoord-1,gamestate.get(xcoord-1).get(ycoord-1)+3);}
		
		if (gamestate.get(xcoord+1).get(ycoord+1)==2){
			gamestate.get(xcoord+1).set(ycoord+1,0);
			gamestate.get(xcoord-1).set(ycoord+1,2);}
		
		if (gamestate.get(xcoord+1).get(ycoord-1)==2){
			gamestate.get(xcoord+1).set(ycoord-1,0);
			gamestate.get(xcoord+1).set(ycoord+1,2);}
		
		if (gamestate.get(xcoord-1).get(ycoord-1)==2){
			gamestate.get(xcoord-1).set(ycoord-1,0);
			gamestate.get(xcoord+1).set(ycoord-1,2);}
		
		if (gamestate.get(xcoord-1).get(ycoord-1)==5){
			gamestate.get(xcoord-1).set(ycoord-1,2);
			gamestate.get(xcoord+1).set(ycoord-1,2);}
		
		if (gamestate.get(xcoord-1).get(ycoord-1)==3){
			gamestate.get(xcoord-1).set(ycoord-1,2);}}
		else {						
			
			//2 units out
			if (xcoord-2>=0&&gamestate.get(xcoord-2).get(ycoord)==2){
				gamestate.get(xcoord-2).set(ycoord,0);
				gamestate.get(xcoord).set(ycoord-2,gamestate.get(xcoord).get(ycoord-2)+3);}
			
			
			if (xcoord-2>=0&&ycoord+2<=9){
			if (gamestate.get(xcoord).get(ycoord+2)==2){
				gamestate.get(xcoord).set(ycoord+2,0);
				gamestate.get(xcoord-2).set(ycoord,2);}}
			
			if (ycoord+2<=9)
			if (gamestate.get(xcoord+2).get(ycoord)==2){
				gamestate.get(xcoord+2).set(ycoord,0);
				gamestate.get(xcoord).set(ycoord+2,2);}
			
			
			if (ycoord-2>=0){
			if (gamestate.get(xcoord).get(ycoord-2)==2){
				gamestate.get(xcoord).set(ycoord-2,0);
				gamestate.get(xcoord+2).set(ycoord,2);}
			
			if (gamestate.get(xcoord).get(ycoord-2)==5){
				gamestate.get(xcoord).set(ycoord-2,2);
				gamestate.get(xcoord+2).set(ycoord,2);}
			
			if (gamestate.get(xcoord).get(ycoord-2)==3){
				gamestate.get(xcoord).set(ycoord-2,2);}}
		}
		
		window.repaint();
	}
	
	private boolean canrotate(int x, int y){// checks if the piece is able to rotate. Special 
		// circumstances for the long piece are used as well
		
		if ((gamestate.get(x).get(y+1)==2&&gamestate.get(x-1).get(y)==1)
				|| (gamestate.get(x).get(y-1)==2&gamestate.get(x+1).get(y)==1)
				|| (gamestate.get(x+1).get(y)==2&&gamestate.get(x).get(y+1)==1)
				|| (gamestate.get(x-1).get(y)==2&&gamestate.get(x).get(y-1)==1)
				)return false;
		
		if (blocktype==0){
			if (y+2<=9&&x==1&&gamestate.get(x).get(y+2)==2)return false;
			if (y-2>=0&&(gamestate.get(x).get(y-2)==2&gamestate.get(x+2).get(y)==1
					|| (x-2>=0&&gamestate.get(x-2).get(y)==2&&gamestate.get(x).get(y-2)==1)))
				return false;
			if (y+2<=9&& 
					(gamestate.get(x+2).get(y)==2&&gamestate.get(x).get(y+2)==1
					|| (x-2>=0&&gamestate.get(x).get(y+2)==2&&gamestate.get(x-2).get(y)==1)))
				return false;
		}
		else 
		if ((gamestate.get(x+1).get(y+1)==2&&gamestate.get(x-1).get(y+1)==1)
		|| (gamestate.get(x+1).get(y-1)==2&&gamestate.get(x+1).get(y+1)==1)
		|| (gamestate.get(x-1).get(y-1)==2&&gamestate.get(x+1).get(y-1)==1)
		|| (gamestate.get(x-1).get(y+1)==2&&gamestate.get(x-1).get(y-1)==1))return false;
		
		return true;}
	
	private int instantblockdrop(){// finds the number of spaces the active piece needs to fall
		// to stop moving
		for (int j=0; j<21; j++) if (endblockdrop(j)) return j;
		
		return 0;
	}
	
	private void finishdrop(int drop){// drops the active piece "drop" spaces, instantly stopping
		// its fall
		if (drop!=0){
		for (int x=0;x<20;x++){
			for (int y=0; y<10; y++){
				if (gamestate.get(19-x).get(y)>=2){
					gamestate.get(drop+19-x).set(y, gamestate.get(19-x).get(y));
					gamestate.get(19-x).set(y,0);
				}
			}
			}
		}
	}
	
	private boolean blocked(String rightorleft, int numb){// Checks if the current piece
		// if able to move to the left or the right. The parameter numb is used for rotations
		for (int x=0; x<20; x++){
			for (int y=0; y<10;y++){
				if (rightorleft=="right"){
				if (gamestate.get(x).get(y)>=numb&&y==9) return true;
				if (gamestate.get(x).get(y)>=numb&&gamestate.get(x).get(y+1)==1) return true;
				}
				else if (rightorleft=="left") {
					if (gamestate.get(x).get(y)>=numb&&y==0) return true;
					if (gamestate.get(x).get(y)>=numb&&gamestate.get(x).get(y-1)==1) return true;
				}}}
		return false;
		}
	
	private void blockspawn(){// spawns a new block at the top of the grid. the block will
		// be placed on the grid and will all be the number "2" which signifies they are moving
		// There will also be a single block "3" which indicates the pivot point at which
		// the block will rotate around. If the grid point in which a new piece is being
		// spawned at is occupied already, gameover().
		blocktype = blockchooser.nextInt(7);
		window.repaint();
		if (blocktype<=3){
		if (gamestate.get(0).get(4)==1||gamestate.get(0).get(5)==1||
			gamestate.get(0).get(6)==1)gameover();
		else{
		gamestate.get(0).set(4, 2);
		gamestate.get(0).set(5, 3);
		gamestate.get(0).set(6, 2);
		if (blocktype==0) {
			if (gamestate.get(0).get(3)==1)gameover();
			else gamestate.get(0).set(3, 2);}
		else {
			if (gamestate.get(1).get(3+blocktype)==1)gameover();
			gamestate.get(1).set(3+blocktype,2);}}}
		
		else {
		if (gamestate.get(0).get(4)==1||gamestate.get(0).get(5)==1||
		gamestate.get(1).get(-1+blocktype)==1||gamestate.get(1).get(blocktype)==1)gameover();
		else if (blocktype==4){
		  gamestate.get(0).set(4, 3);
		  gamestate.get(0).set(5, 2);}
		else if (blocktype==6){
		  gamestate.get(0).set(4, 2);
		  gamestate.get(0).set(5, 3);}// hardcoded in order to place correct pivot points
		else if (blocktype==5) {
		  gamestate.get(0).set(4, 2);
		  gamestate.get(0).set(5, 2);}
		
		// Pieces are randomly generated out of the 7 possible combinations of 4 blocks
		
		gamestate.get(1).set(-1+blocktype, 2);
		gamestate.get(1).set(blocktype, 2);}
		window.repaint();
		blockdrop();// makes the piece drop
	}
	
	private void gameover(){// switches screen to game over screen
		gamescreen=2;
		window.repaint();}
	
	private void blockdrop(){// creates a block and drops it
		while (!endblockdrop(0)){//until the dropping piece hits the bottom or another piece
			// it will drop block by block
			for (int x=0; x<20; x++){
				for (int y=0; y<10; y++){
					if (gamestate.get(19-x).get(y)>=2){
						gamestate.get(20-x).set(y,gamestate.get(19-x).get(y));
						gamestate.get(19-x).set(y,0);
					}
				}
			}
			window.repaint();
			try{Thread.sleep(20);}
			catch(Exception exp){System.out.println("Runtime Error");}
		}
		// when the piece hits the bottom, it connects with all the other pieces
		for (int x=0; x<20; x++){
			for (int y=0; y<10; y++){
				if (gamestate.get(x).get(y)>=2){
					gamestate.get(x).set(y, 1);
				}
			}
		}
		scored();//checks if the dropped block has made a line
		blockspawn();// creates another block
		}
		
	private void scored(){//checks if the current gamestate contains a line of blocks
		for (int x=0; x<20; x++){
			boolean scored=true;
			for (int y=0; y<10; y++){
				if (gamestate.get(x).get(y)!=1){
					scored=false;
				}
			}
			if (scored){// if there is a line of blocks, the line is deleted
				score+=100;
				for (int y=0; y<10; y++) gamestate.get(x).set(y, 0);
				window.repaint();
				try{Thread.sleep(400);}catch(Exception exp){System.out.println("Runtime Error");}
				linecreated(x);}
		}
	}
	
	private void linecreated(int lineindex){// shifts all the blocks above the deleted line
		// down one block. Follows the original design of Tetris which did not have "gravity"
		// or chain reactions
		for (int x=(lineindex-1); x>=0; x--){
			for (int y=0; y<10; y++){
				gamestate.get(x+1).set(y, gamestate.get(x).get(y));
			}
		}
	}
	
	private boolean endblockdrop(int j){// if the current block piece is touching either
		// the bottom of the grid or another already placed piece, it will stop moving
		boolean end=false;
		for (int x=0; x<20; x++){
			for (int y=0; y<10; y++){
				if ((gamestate.get(x).get(y)>=2)&&(gamestate.get(x+j+1).get(y)==1)){
					end=true;
				}
			}
		}
		return end;
	}
}
