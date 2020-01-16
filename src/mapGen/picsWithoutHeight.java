package mapGen;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class picsWithoutHeight extends JPanel {

	//backup file before implementing height shift (as hight shift messes up current rules). Check ../assets/robinSeed.png for results
	
	//0 normal
	//1-4 left, top, right, bottom border 
	//5-8 top-left, top-right, right-bottom, bottom-left diagonal
	//9-12 top-left, top-right, right-bottom, bottom-left inverted
	//13-16	top-left, top-right, right-bottom, bottom-left rounded
		private static final long serialVersionUID = 1L;
		static BufferedImage[] stoneBlue= new BufferedImage[17];
		static BufferedImage[] stoneGray= new BufferedImage[17];
		static BufferedImage[] sand= new BufferedImage[17];
		static BufferedImage[] water= new BufferedImage[17];
		static BufferedImage[] neutralDeco= new BufferedImage[5];
		static BufferedImage[] sandDeco= new BufferedImage[2];
		static BufferedImage wall;
		
        static int width = 100;
        static int height = 100;      
        static int expandedWidth = width*2;
        static int expandedHeight = height*2;  
        static int expandedWorld[][] = new int[expandedWidth][expandedHeight];
        static boolean doubleTile[][] = new boolean[expandedWidth][expandedHeight];
    	static int[][] worldHeightMap = new int[expandedWidth][expandedHeight];
        static int size = 50;
		static Random generator;

	    public static int generateNumber(){
	    	return (int)(Math.round(generator.nextDouble()*3));
	    }
	    
	    public static boolean sameNotOut (int []ar)
	    {
	    	boolean same = true;
	    	int comparer = ar[0];
	    	for(int i =1;i<ar.length;i++)
	    	{
	    		same = comparer == ar[i] && ar[i]!=-1;
	    		if(!same)
	    		{
	    			return false;
	    		}
	    	}
	    	return true;
	    }
	    
	    public BufferedImage getImage (int type, int id)
	    {
	    	switch(type)
	    	{
	    	case 0:
	    		return stoneBlue[id];
	    	case 1:
	    		return stoneGray[id];
	    	case 2:
	    		return  sand[id];
	    	case 3:
	    		return water[id];
	    	default:
	    		return stoneGray[id];
	    	}
	    }
	    
	    public BufferedImage[] getImagesByNumber(int current, int i, int j) {
	    	boolean fake = true;
	    	if (fake)
	    		return new BufferedImage[] {getImage(current,0)};
	    	
	    	int left = -1;
	    	//has left field 2 images?
	    	boolean leftDouble = false;
	    	if(j>0) {
	    		leftDouble= doubleTile[i][j-1];	
	    		left= expandedWorld[i][j-1];	
	    	}

	    	int top = -1;
	    	boolean topDouble = false;
    		if(i>0) {
    			topDouble = doubleTile[i-1][j];
    			top = expandedWorld[i-1][j];
    		}
	    	  	
	    	int right = -1;
	    	boolean rightDouble = false;
    		if(j<(expandedWidth)-1) {
    			rightDouble = doubleTile[i][j+1];
    			right = expandedWorld[i][j+1];
    		}
	    	int bottom = -1;
	    	boolean bottomDouble = false;
    		if(i<(expandedHeight)-1) {
    			bottomDouble = doubleTile[i+1][j];
    			bottom = expandedWorld[i+1][j];
    		}
    		
	    	int topLeft = -1;
    		if(i>0 && j>0) {
    			topLeft = expandedWorld[i-1][j-1];
    		}
    		
	    	int topRight = -1;
    		if(i>0 && j<(expandedWidth)-1) {
    			topRight = expandedWorld[i-1][j+1];
    		}
    		
	    	int bottomLeft = -1;
    		if(i<(expandedHeight)-1 && j>0) {
    			bottomLeft = expandedWorld[i+1][j-1];
    		}
    		
	    	int bottomRight = -1;
    		if(i<(expandedHeight)-1 && j<(expandedWidth)-1) {
    			bottomRight = expandedWorld[i+1][j+1];
    		}

    		//replace current with inverted of top and rounded of current
    		if(sameNotOut(new int[]{top,left}) && sameNotOut(new int[]{current,bottom, right}) && current != top && !topDouble && !leftDouble)
    		{
        		//System.out.println("i " + i + " j " + j);
    			//System.out.println("before bottom" +  bottomDouble +" and right " + rightDouble +" and top " + topDouble +" and left " + leftDouble);
    			//System.out.println("inverted rounded 1" +  topDouble +" and " + leftDouble);
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,15),getImage(top,9)};
    		}
    		
    		//replace current with inverted of top and rounded of current
    		if(sameNotOut(new int[]{top,right}) && sameNotOut(new int[]{current,bottom, left}) && current != top && !topDouble && !rightDouble)
    		{
    			//System.out.println("i " + i + " j " + j);
    		//	System.out.println("before bottom" +  bottomDouble +" and right " + rightDouble +" and top " + topDouble +" and left " + leftDouble);
    		//	System.out.println("inverted rounded 2" +  topDouble +" and " + rightDouble);
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,16),getImage(top,10)};
    		}
    		
    		//replace current with inverted of bottom and rounded of current
    		if(sameNotOut(new int[]{bottom,left}) && sameNotOut(new int[]{current,top, right}) && current != bottom && !bottomDouble && !leftDouble)
    		{
//    			System.out.println("i " + i + " j " + j);
//    			System.out.println("before bottom" +  bottomDouble +" and right " + rightDouble +" and top " + topDouble +" and left " + leftDouble);
//    			System.out.println("inverted rounded 3" +  bottomDouble +" and " + leftDouble);
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,14),getImage(bottom,12)};
    		}
    		
    		//replace current with inverted of bottom and rounded of current
    		if(sameNotOut(new int[]{bottom,right}) && sameNotOut(new int[]{current,top, left}) && current != bottom && !bottomDouble && !rightDouble)
    		{
//    			System.out.println("i " + i + " j " + j);
//    			System.out.println("before bottom" +  bottomDouble +" and right " + rightDouble +" and top " + topDouble +" and left " + leftDouble);
//    			System.out.println("inverted rounded 4" +  bottomDouble +" and " + rightDouble);
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,13),getImage(bottom,11)};
    		}
    		

	    	
    		//4 current != right != bottom != bottom right, dont use is default
    		//4 current != left != bottom != bottom left => left bottom of left, top right of current
    		if(current != left && current != bottom && current != bottomLeft && left != bottom && left != bottomLeft && bottom != bottomLeft && left != -1 && bottom != -1 && bottomLeft != -1)
    		{
    			doubleTile[i][j] = true;
    			//diagonal solution
        		//return new BufferedImage[] {getImage(current,6),getImage(left,8)};	
    			
    			//rounded solution
        		return new BufferedImage[] {getImage(current,14),getImage(left,12)};	
    		}
    		
    		//4 current != top != right != top right => top right of top, bottom left of current
     		if(current != top && current != right && current != topRight && top != right && top != topRight && right != topRight && top != -1 && right != -1 && topRight != -1)
     		{
    			doubleTile[i][j] = true;
    			//diagonal solution
        		//return new BufferedImage[] {getImage(current,8),getImage(top,6)};		
    			
    			//rounded solution
        		return new BufferedImage[] {getImage(current,16),getImage(top,10)};		
    		}
     		
    		//4 current != top != left != top left => top left of top left, bottom right of current
    		if(current != top && current != left && current != topLeft && top != left && top != topLeft && left != topLeft && top != -1 && left != -1 && topLeft != -1)
    		{
    			doubleTile[i][j] = true;
    			//diagonal solution
        		//return new BufferedImage[] {getImage(current,7),getImage(topLeft,5)};		
    			
    			//rounded solution
    			return new BufferedImage[] {getImage(current,15),getImage(topLeft,9)};		
    		}
    		
    		
    		//3 point issue left
    		if(sameNotOut(new int[]{left,bottomLeft}) && bottom != current && bottom != -1 && left != current && left!=bottom)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,14),getImage(left,12)};
    				
    		}
    		
    		//3 point issue left
    		if(sameNotOut(new int[]{left,topLeft}) && top != current && top != -1 && left != current && left!=top)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,15),getImage(left,9)};
    		}
    		
    		//3 point issue right
    		if(sameNotOut(new int[]{right,bottomRight}) && bottom != current && bottom != -1 && right != current && bottom!=right)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,13),getImage(right,11)};
    		}
    		
    		//3 point issue right
    		if(sameNotOut(new int[]{right,topRight}) && top != current && top != -1 && right != current && right!=top)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,16),getImage(right,10)};
    		}
    		
    		//3 point issue top
    		if(sameNotOut(new int[]{top,topRight}) && right != current && right != -1 && top != current && right!=top)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,16),getImage(top,10)};
    		}
    		
    		//3 point issue top
    		if(sameNotOut(new int[]{top,topLeft}) && left != current && left != -1 && top != current && left!=top)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,15),getImage(top,9)};
    		}
    		
    		//3 point issue bottom
    		if(sameNotOut(new int[]{bottom,bottomRight}) && right != current && right != -1 && bottom != current && bottom!=right)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,13),getImage(bottom,11)};
    		}
    		
    		//3 point issue top
    		if(sameNotOut(new int[]{bottom,bottomLeft}) && left != current && left != -1 && bottom != current && left!=bottom)
    		{
    			doubleTile[i][j] = true;
        		return new BufferedImage[] {getImage(current,14),getImage(bottom,12)};
    		}
    		
    		//replace with left border
    		if(left!=-1 && current != left && !leftDouble)
    		{
        		return new BufferedImage[] {getImage(current,1)};
    		}
    		
    		//replace with top border
    		if(top!=-1 && current != top && !topDouble)
    		{
        		return new BufferedImage[] {getImage(current,2)};
    		}
    		
    		//replace with right border
    		if(right!=-1 && current != right && !rightDouble)
    		{
        		return new BufferedImage[] {getImage(current,3)};
    		}
    		
    		//replace with bottom border
    		if(bottom!=-1 && current != bottom && !bottomDouble)
    		{
        		return new BufferedImage[] {getImage(current,4)};
    		}
    		double nextDouble = Math.random();
    		int nextInt = (int)Math.round(nextDouble*5);
    		if(nextInt==1)
    			{
    			//with deko
    			if(current ==2)
    			{
    				//50/50 to take sand or default
    				if(((int)Math.round(Math.random()*2))==1)	
    				{
    					int rand = (int)Math.floor(Math.random()*sandDeco.length);
    					return new BufferedImage[] {getImage(current,0), sandDeco[rand]};
    				}
    			}
				int rand = (int)Math.floor(Math.random()*neutralDeco.length);
				return new BufferedImage[] {getImage(current,0), neutralDeco[rand]};
    			}
    		return new BufferedImage[] {getImage(current,0)};

	    }
	    
	    public void paintComponent(Graphics g) {

	        super.paintComponent(g);
	       // Graphics2D g2 = (Graphics2D) g;
	       // g2.fillOval(x - 2, y - 2, 4, 4);

	        //draw all
//	        for(int i = 0;i<stoneBlue.length;i++)
//	        {
//	        int offsetLeft = i*size;
//	        g.drawImage(stoneBlue[i], offsetLeft, 0, size, size, null);
//	        g.drawImage(stoneGray[i], offsetLeft, size, size, size, null);	
//	        g.drawImage(water[i], offsetLeft, size*2, size, size, null);	
//	        g.drawImage(sand[i], offsetLeft, size*3, size, size, null);	
//	        }
	        
			g.setColor(Color.white);
	        
	        for(int i = 0;i<expandedHeight;i++)
	        {
		        int offsetTop = i*size; //+ size*4;
	            for(int j = 0;j<expandedWidth;j++)
	            {
	    	    int offsetLeft = j*size;
	    	    BufferedImage[] images = getImagesByNumber(expandedWorld[i][j], i, j);
	    	    for(int k = 0;k<images.length;k++)
	    	    {
	    	    	if(images[k]==sandDeco[1])
	    	    	{
	    	    		//palm is 150x100
	    	    		 g.drawImage(images[k], offsetLeft-(size*2), offsetTop-size, size*3, size*2, null);	
	    	    		 g.drawString(worldHeightMap[i][j]+"", offsetLeft+10, offsetTop+10);
	    	    		 
	    	    	}
	    	    	else
	    	    	{	
	    	    		//basic 50x50
			    	    g.drawImage(images[k], offsetLeft, offsetTop, size, size, null);
			    	    g.drawString(worldHeightMap[i][j]+"", offsetLeft+10, offsetTop+10);
	    	    	}

	    	    }

	            }
	        }

	    }


	   
	    
	public static void generateExpandedMap()
	{
        for(int i = 0;i<height;i++)
        {
        	int offsetHeight = i*2;
            for(int j = 0;j<width;j++)
            {
            	int offsetWidth = 2*j;
            int num = generateNumber();
            expandedWorld[offsetHeight][offsetWidth]=num;
            expandedWorld[offsetHeight][offsetWidth+1]=num;
            expandedWorld[offsetHeight+1][offsetWidth]=num;
            expandedWorld[offsetHeight+1][offsetWidth+1]=num;
            }
        }
	}
	
	public static void removeSingleTiles(int arrayToClean[][])
	{
		boolean hasChanged = false;
		int loop = 0;
		do {
			loop ++;
			hasChanged = false;
			for(int i = 0;i<expandedHeight;i++)
	        {
	            for(int j = 0;j<expandedWidth;j++)
	            {
	            	int current = arrayToClean[i][j];
	            	int left = -1;
	            	if(j>0) {
	            		left= arrayToClean[i][j-1];	
	            	}

	            	int top = -1;
	        		if(i>0) {
	        			top = arrayToClean[i-1][j];
	        		}
	            	  	
	            	int right = -1;
	        		if(j<(expandedWidth)-1) {
	        			right = arrayToClean[i][j+1];
	        		}
	            	int bottom = -1;
	        		if(i<(expandedHeight)-1) {
	        			bottom = arrayToClean[i+1][j];
	        		}
	        		
	            	int topLeft = -1;
	        		if(i>0 && j>0) {
	        			topLeft = arrayToClean[i-1][j-1];
	        		}
	        		
	            	int topRight = -1;
	        		if(i>0 && j<(expandedWidth)-1) {
	        			topRight = arrayToClean[i-1][j+1];
	        		}
	        		
	            	int bottomLeft = -1;
	        		if(i<(expandedHeight)-1 && j>0) {
	        			bottomLeft = arrayToClean[i+1][j-1];
	        		}
	        		
	            	int bottomRight = -1;
	        		if(i<(expandedHeight)-1 && j<(expandedWidth)-1) {
	        			bottomRight = arrayToClean[i+1][j+1];
	        		}
	        		boolean blockOfFour = sameNotOut(new int[]{top,left, topLeft, current}) || sameNotOut(new int[]{top,right, topRight, current}) ||sameNotOut(new int[]{bottom,left, bottomLeft, current}) ||sameNotOut(new int[]{bottom,right, bottomRight, current}) ;
	        		//tile has at least four blocks, take no action as its smoothable
	        		
	        		
	        		if(!blockOfFour)
	        		{
	        			hasChanged=true;
	        			int[] count = new int[11];
	        			//count occurences of tile values surrounding current
	        			if(top!=-1)
	        			{
	        				count[top]++;	
	        			}
	        			if(bottom!=-1)
	        			{
	        				count[bottom]++;	
	        			}
	        			if(left!=-1)
	        			{
	        				count[left]++;
	        			}
	        			if(right!=-1)
	        			{
	        				count[right]++;
	        			}
	        			if(topLeft!=-1)
	        			{
	        				count[topLeft]++;	
	        			}
	        			if(bottomLeft!=-1)
	        			{
	        				count[bottomLeft]++;	
	        			}
	        			if(topRight!=-1)
	        			{
	        				count[topRight]++;
	        			}
	        			if(bottomRight!=-1)
	        			{
	        				count[bottomRight]++;
	        			}
	        			//get highest value and change current to that type
	        			int maxAt = 0;
	        			if(current==0)
	        				maxAt = 1;
	        			for (int k = maxAt+1; k < count.length; k++) {
	        			    maxAt = count[k] > count[maxAt] && k!=current ? k : maxAt;
	        			}
	        			arrayToClean[i][j] = maxAt;
	        		}
	        	}
	    	}			
			
		}
		while(hasChanged&&loop<10);
	}
	
	public static void generateBiomMap()
	{
		int worldWidth = expandedWidth;
    	int worldHeight = expandedHeight;

    	int[][] heatMap = new int[worldWidth][worldHeight];
    	int[][] rainMap = new int[worldWidth][worldHeight];
    	
    	partWorld(0,0,worldWidth, worldHeight, 5, 1, 0, 10, worldHeightMap, 1, 0, 0.3);
    	partWorld(0,0,worldWidth, worldHeight, 50, 100, 0, 100, heatMap, 5, 30, 1);
    	partWorld(0,0,worldWidth, worldHeight, 50, 100, 0, 100, rainMap, 5, 30, 1);
    	
    	for(int i = 0;i<worldHeight;i++)
    	{
        	for(int j = 0;j<worldWidth;j++)
        	{
        	if(heatMap[i][j]<=50)
        	{
            	if(rainMap[i][j]<=50)
            	{
            	//0-50 temp + 0-50 feucht => sand
            		expandedWorld[i][j] = 2;
            	}
            	else
            	{
            	//0-50 temp + 50-100 feucht => blue stone
            		expandedWorld[i][j] = 0;
            	}
        	}
        	else
        	{
            	if(rainMap[i][j]<=50)
            	{
            	//50-100 temp + 0-50 feucht => black stone	
            		expandedWorld[i][j] = 1;
            	}
            	else
            	{
            	//50-100 temp + 50-100 feucht => water	
            		expandedWorld[i][j] = 3;
            	}
        	}
        	}
    	}
	}
	
	private static void partWorld(int startX, int startY, int width, int height, int setting, int maxDiffSetting, int minSetting, int maxSetting, int[][] result, int minDiff, int diffMinusPerIteration, double probability)
	    {
	    	int cappedSetting = setting;
	    	if(setting < minSetting)
	    		cappedSetting = minSetting;
	    	else if(setting > maxSetting)
	    		cappedSetting = maxSetting;
	    	int nextMaxDiffSetting = Math.max(minDiff, maxDiffSetting-diffMinusPerIteration);
	    	if(width==1 && height == 1)
	    	{
	    		//write to retult array at pos
	    		result[startX][startY]=cappedSetting;
	    	}
	    	else if (width == 1)
	    	{
	    		//part height in 2
	    		int randY = generateBetween(1, height-1, 1.0);
	    		partWorld(startX, startY, 1, randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    		partWorld(startX, startY+randY, 1, height-randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    	}
	    	else if (height == 1)
	    	{
	    		//part width in 2
	    		int randX = generateBetween(1, width-1, 1.0);
	    		partWorld(startX, startY, randX, 1, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    		partWorld(startX+randX, startY, width-randX, 1, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    	}
	    	else
	    	{
	    		int randX = generateBetween(1, width-1, 1.0);
	    		int randY = generateBetween(1, height-1, 1.0);
	    		partWorld(startX, startY, randX, randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    		partWorld(startX+randX, startY, width-randX, randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    		partWorld(startX, startY+randY, randX, height-randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    		partWorld(startX+randX, startY+randY, width-randX,  height-randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
	    	}
	    }
	
    private static int generateBetween (int min, int max, double probability)
    {
    	
    	int diff = max-min;
    	int value = (int)(Math.round(generator.nextDouble()*diff))+min;
    	boolean shouldUse = (generator.nextDouble()*100<probability*100);
    	if(shouldUse)
    		return value;
    	return 0;
    }
    
    public static void generateNoiseMap() {
    	//3, 0.2f, 0.05f
    	int worldWidth = expandedWidth;
    	int worldHeight = expandedHeight;

    	int[][] heatMap = new int[worldWidth][worldHeight];
    	int[][] rainMap = new int[worldWidth][worldHeight];
    	
    	int offsetHeight = (int)(generator.nextDouble()*100);
    	int offsetHeat = (int)(generator.nextDouble()*100);
    	int offsetRain = (int)(generator.nextDouble()*100);
    	
    	worldHeightMap = SimplexNoise.noiseMap(worldWidth, worldHeight, 3,0.2f, 0.05f,offsetHeight,offsetHeight);
    	//reduce height to layers
        for(int y = 0;y<expandedHeight;y++)
        {
            for(int x = 0;x<expandedWidth;x++)
            {
            	worldHeightMap[y][x] = (int)Math.round(worldHeightMap[y][x]/10);
            }
        }  
    	
    	heatMap= SimplexNoise.noiseMap(worldWidth, worldHeight, 3,0.2f, 0.05f,offsetHeat,offsetHeat);
    	rainMap= SimplexNoise.noiseMap(worldWidth, worldHeight, 3,0.2f, 0.05f,offsetRain,offsetRain);

    	
    	for(int i = 0;i<worldHeight;i++)
    	{
        	for(int j = 0;j<worldWidth;j++)
        	{
        	if(heatMap[i][j]<=50)
        	{
            	if(rainMap[i][j]<=50)
            	{
            	//0-50 temp + 0-50 feucht => sand
            		expandedWorld[i][j] = 2;
            	}
            	else
            	{
            	//0-50 temp + 50-100 feucht => blue stone
            		expandedWorld[i][j] = 0;
            	}
        	}
        	else
        	{
            	if(rainMap[i][j]<=50)
            	{
            	//50-100 temp + 0-50 feucht => black stone	
            		expandedWorld[i][j] = 1;
            	}
            	else
            	{
            	//50-100 temp + 50-100 feucht => water	
            		expandedWorld[i][j] = 3;
            	}
        	}
        	}
    	}
    }
	    
    public static long seedHash(String seed) {
    	 long returnValue = 0l;
    	  try
          {
    		  String longString ="";
              // Create MD5 Hash
              MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
              digest.update(seed.getBytes());
              byte messageDigest[] = digest.digest();
              // Create Hex String
              StringBuffer hexString = new StringBuffer();
              for (int i = 0; i < messageDigest.length; i++)
              {
                  hexString.append(String.format("%d", messageDigest[i]));
              }
              // guid = hexString.toString().substring(24);
              longString = hexString.toString().toLowerCase();
              returnValue=Long.parseLong(longString.replace("-", "").substring(0, 18));
          }
          catch (NoSuchAlgorithmException | NullPointerException e)
          {
              System.out.println(e);
          }
    	  return returnValue;
    }
    
    public static void adjustHeightMap() {
    	
    }
    
    public static void main(String args[]) throws IOException
    {
    	JFrame frame = new JFrame("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        picsWithoutHeight ex = new picsWithoutHeight();
        JScrollPane scroll = new JScrollPane(ex);
        frame.getContentPane().add(scroll);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        long seed = seedHash("Robin");
        //fill with false
        Arrays.stream(doubleTile).forEach(a -> Arrays.fill(a, false));
        
        //init Generator
        generator = new Random(seed);
        
        //generateBiomMap();
        generateNoiseMap();
        removeSingleTiles(expandedWorld);
        removeSingleTiles(worldHeightMap);
        
        
    	String base = "C:\\Users\\Robin\\Dropbox\\Games\\Tiles\\Fertig (50x50, Schatten, Dither)";
    	 try {   
    		 //wall
    		 wall =  ImageIO.read(new File("C:\\Users\\Robin\\Dropbox\\Games\\Tiles\\Zu Bearbeiten\\Höhlenwände\\wand_oben3.png"));
    				
    		 
    		 //blue stone tiles
    		 stoneBlue[0] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\stone1.0000.png"));
    		 stoneBlue[1] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\stone1BorderLeft.0000.png"));
    		 stoneBlue[2] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\stone1BorderTop.0000.png"));
    		 stoneBlue[3] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\stone1BorderRight.0000.png"));
    		 stoneBlue[4] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\stone1BorderBottom.0000.png"));
    		 stoneBlue[5] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Diagonale_stein2\\stone2_diagonal1.0000.png"));
    		 stoneBlue[6] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Diagonale_stein2\\stone2_diagonal2.0000.png"));
    		 stoneBlue[7] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Diagonale_stein2\\stone2_diagonal3.0000.png"));
    		 stoneBlue[8] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Diagonale_stein2\\stone2_diagonal4.0000.png"));
    		 stoneBlue[9] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke invertiert_stein2\\stone2_ecke_invertiert2.0000.png"));
    		 stoneBlue[10] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke invertiert_stein2\\stone2_ecke_invertiert1.0000.png"));
    		 stoneBlue[11] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke invertiert_stein2\\stone2_ecke_invertiert4.0000.png"));
    		 stoneBlue[12] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke invertiert_stein2\\stone2_ecke_invertiert3.0000.png"));
    		 stoneBlue[13] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke normal_stein2\\stone2_ecke_normal3.0000.png"));
    		 stoneBlue[14] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke normal_stein2\\stone2_ecke_normal4.0000.png"));
    		 stoneBlue[15] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke normal_stein2\\stone2_ecke_normal1.0000.png"));
    		 stoneBlue[16] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden blau\\Ecke normal_stein2\\stone2_ecke_normal2.0000.png"));
    		 	
    		 //gray stone tiles
    		 stoneGray[0] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\stoneNew2.0000.png"));
    		 stoneGray[1] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\stoneBorderLeft.0000.png"));
    		 stoneGray[2] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\stoneBorderTop.0000.png"));
    		 stoneGray[3] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\stoneBorderRight.0000.png"));
    		 stoneGray[4] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\stoneBorderBottom.0000.png"));
    		 stoneGray[5] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Diagonale\\stone_diagonal2New.0000.png"));
    		 stoneGray[6] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Diagonale\\stone_diagonal3New.0000.png"));
    		 stoneGray[7] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Diagonale\\stone_diagonal4New.0000.png"));
    		 stoneGray[8] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Diagonale\\stone_diagonal1new.0000.png"));
    		 stoneGray[9] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke invertiert\\stone_ecke_invertier2.0000.png"));
    		 stoneGray[10] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke invertiert\\stone_ecke_invertier1.0000.png"));
    		 stoneGray[11] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke invertiert\\stone_ecke_invertier4.0000.png"));
    		 stoneGray[12] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke invertiert\\stone_ecke_invertier3.0000.png"));
    		 stoneGray[13] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke normal\\stone_ecke_normal4.0000.png"));
    		 stoneGray[14] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke normal\\stone_ecke_normal1.0000.png"));
    		 stoneGray[15] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke normal\\stone_ecke_normal2.0000.png"));
    		 stoneGray[16] = ImageIO.read(new File(base+"\\Steinboden\\Steinboden grau\\Ecke normal\\stone_ecke_normal3.0000.png"));
    		 
    		 //water tiles
    		 water[0] = ImageIO.read(new File(base+"\\Wasser\\wasserNew.0000.png"));
    		 water[1] = ImageIO.read(new File(base+"\\Wasser\\wasserBorderLeft.0000.png"));
    		 water[2] = ImageIO.read(new File(base+"\\Wasser\\wasserBorderTop.0000.png"));
    		 water[3] = ImageIO.read(new File(base+"\\Wasser\\wasserBorderRight.0000.png"));
    		 water[4] = ImageIO.read(new File(base+"\\Wasser\\wasserBorderBottom.0000.png"));
    		 water[5] = ImageIO.read(new File(base+"\\Wasser\\Diagonale\\wasser_diagonal4.0000.png"));
    		 water[6] = ImageIO.read(new File(base+"\\Wasser\\Diagonale\\wasser_diagonal3.0000.png"));
    		 water[7] = ImageIO.read(new File(base+"\\Wasser\\Diagonale\\wasser_diagonal2.0000.png"));
    		 water[8] = ImageIO.read(new File(base+"\\Wasser\\Diagonale\\wasser_diagonal1.0000.png"));
    		 water[9] = ImageIO.read(new File(base+"\\Wasser\\Ecke invertiert\\wasser_oben_links_invertiert.0000.png"));
    		 water[10] = ImageIO.read(new File(base+"\\Wasser\\Ecke invertiert\\wasser_oben_rechts_invertiert.0000.png"));
    		 water[11] = ImageIO.read(new File(base+"\\Wasser\\Ecke invertiert\\wasser_unten_rechts_invertiert.0000.png"));
    		 water[12] = ImageIO.read(new File(base+"\\Wasser\\Ecke invertiert\\wasser_unten_links_invertiert.0000.png"));
    		 water[13] = ImageIO.read(new File(base+"\\Wasser\\Ecke normal\\wasser_oben_links_normal.0000.png"));
    		 water[14] = ImageIO.read(new File(base+"\\Wasser\\Ecke normal\\wasser_oben_rechts_normal.0000.png"));
    		 water[15] = ImageIO.read(new File(base+"\\Wasser\\Ecke normal\\wasser_unten_rechts_normal.0000.png"));
    		 water[16] = ImageIO.read(new File(base+"\\Wasser\\Ecke normal\\wasser_unten_links_normal.0000.png"));
    		 
    		 //sand tiles
    		 sand[0] = ImageIO.read(new File(base+"\\Sand\\wueste.0000.png"));
    		 sand[1] = ImageIO.read(new File(base+"\\Sand\\wuesteBorderLeft.png"));
    		 sand[2] = ImageIO.read(new File(base+"\\Sand\\wuesteBorderTop.png"));
    		 sand[3] = ImageIO.read(new File(base+"\\Sand\\wuesteBorderRight.png"));
    		 sand[4] = ImageIO.read(new File(base+"\\Sand\\wuesteBorderBottom.png"));
    		 sand[5] = ImageIO.read(new File(base+"\\Sand\\Diagonale done\\wueste_diagonal1.0000.png"));
    		 sand[6] = ImageIO.read(new File(base+"\\Sand\\Diagonale done\\wueste_diagonal2.0000.png"));
    		 sand[7] = ImageIO.read(new File(base+"\\Sand\\Diagonale done\\wueste_diagonal3.0000.png"));
    		 sand[8] = ImageIO.read(new File(base+"\\Sand\\Diagonale done\\wueste_diagonal4.0000.png"));
    		 sand[9] = ImageIO.read(new File(base+"\\Sand\\Ecke invertiert\\wueste_ecke_invertiert1.0000.png"));
    		 sand[10] = ImageIO.read(new File(base+"\\Sand\\Ecke invertiert\\wueste_ecke_invertiert4.0000.png"));
    		 sand[11] = ImageIO.read(new File(base+"\\Sand\\Ecke invertiert\\wueste_ecke_invertiert2.0000.png"));
    		 sand[12] = ImageIO.read(new File(base+"\\Sand\\Ecke invertiert\\wueste_ecke_invertiert3.0000.png"));
    		 sand[13] = ImageIO.read(new File(base+"\\Sand\\Ecke normal\\wueste_ecke_normal1.0000.png"));
    		 sand[14] = ImageIO.read(new File(base+"\\Sand\\Ecke normal\\wueste_ecke_normal2.0000.png"));
    		 sand[15] = ImageIO.read(new File(base+"\\Sand\\Ecke normal\\wueste_ecke_normal3.0000.png"));
    		 sand[16] = ImageIO.read(new File(base+"\\Sand\\Ecke normal\\wueste_ecke_normal4.0000.png"));
    		 
    		 //sand deco
    		 sandDeco[0] = ImageIO.read(new File(base+"\\Sand\\Deko\\skull.0000.png"));
    		 sandDeco[1] = ImageIO.read(new File(base+"\\Sand\\Deko\\palme.0001.png"));
    		 
    		 //neutral deco
    		 neutralDeco[0] = ImageIO.read(new File(base+"\\Nicht zugeordnet, allgemein\\steine_1.0000.png"));
    		 neutralDeco[1] = ImageIO.read(new File(base+"\\Nicht zugeordnet, allgemein\\steine_2.0000.png"));
    		 neutralDeco[2] = ImageIO.read(new File(base+"\\Nicht zugeordnet, allgemein\\steine_3.0000.png"));
    		 neutralDeco[3] = ImageIO.read(new File(base+"\\Nicht zugeordnet, allgemein\\steine_4.0000.png"));
    		 neutralDeco[4] = ImageIO.read(new File("C:\\Users\\Robin\\Dropbox\\Games\\Tiles\\Zu Bearbeiten\\Robin\\plant2.png"));
    		
    		 
          } catch (IOException exe) {
               // handle exception...
          }

    }

	
}
