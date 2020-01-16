package mapGen;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class picRounder {
	/*
	 * Takes a input file and cuts it to 12 new pngs with alpha channel (used for our gametiles)
	 * 4 border (top, left, right, bottom)
	 * 4 rounded corners
	 * 4 straight corners
	 * */
		static Color c = new Color(255, 255, 255, 0);
		static AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
		
		private static BufferedImage copyImage(BufferedImage source) {
			BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = b.createGraphics();
			g.drawImage(source, 0, 0, null);
			g.dispose();
			return b;
		}
		
		private static Graphics2D createGraphic(BufferedImage image) {
			Graphics2D g = image.createGraphics();
			g.setComposite(composite);
			g.setColor(c);
			g.setBackground(c);
			return g;
		}
		
		private static void writeAndFlush (String filename, BufferedImage image ) throws IOException {
			File outputfile = new File(filename);
			ImageIO.write(image, "png", outputfile);
			image.flush();
		}
		

		public static void main(String[] args) throws Exception {
			String path = "assets/";
			String tileName = "holz";
			String prefix = path+tileName+"_";
			String fileName = "holz_1.0000.png";
			BufferedImage original = ImageIO.read(new File(path+fileName));

			BufferedImage topLeftInverted = copyImage(original);
			Graphics2D g = createGraphic(topLeftInverted);
			g.fillOval(00, 00, 100, 100);
			writeAndFlush(prefix+"topLeftInverted.png", topLeftInverted);
			g.dispose();

			BufferedImage topRightInverted = copyImage(original);
			g = createGraphic(topRightInverted);
			g.fillOval(-51, 0, 100, 100);
			writeAndFlush(prefix+"topRightInverted.png", topRightInverted);
			g.dispose();

			BufferedImage bottomRightInverted = copyImage(original);
			g = createGraphic(bottomRightInverted);
			g.fillOval(-51, -51, 100, 100);
			writeAndFlush(prefix+"bottomRightInverted.png", bottomRightInverted);
			g.dispose();

			BufferedImage bottomLeftInverted = copyImage(original);
			g = createGraphic(bottomLeftInverted);
			g.fillOval(0, -51, 100, 100);
			writeAndFlush(prefix+"bottomLeftInverted.png", bottomLeftInverted);
			g.dispose();

			BufferedImage topLeftRounded = copyImage(original);
			g = createGraphic(topLeftRounded);
			for (int x = 1; x < 51; x++) {
				for (int y = 1; y < 51; y++) {
					//
					if (Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) > 50.0) {
						g.fillRect(x - 1, y - 1, 1, 1);
					}
				}
			}
			writeAndFlush(prefix+"topLeftRounded.png", topLeftRounded);
			g.dispose();
			
			BufferedImage topRightRounded = copyImage(original);
			g = createGraphic(topRightRounded);
			for (int x = 1; x < 51; x++) {
				for (int y = 1; y < 51; y++) {
					//
					if (Math.round(Math.sqrt(Math.pow(51 - x, 2) + Math.pow(y, 2))) > 50.0) {
						g.fillRect(x - 1, y - 1, 1, 1);
					}
				}
			}
			writeAndFlush(prefix+"topRightRounded.png", topRightRounded);
			g.dispose();

			BufferedImage bottomRightRounded = copyImage(original);
			g = createGraphic(bottomRightRounded);
			for (int x = 1; x < 51; x++) {
				for (int y = 1; y < 51; y++) {
					//
					if (Math.round(Math.sqrt(Math.pow(51 - x, 2) + Math.pow(51 - y, 2))) > 50.0) {
						g.fillRect(x - 1, y - 1, 1, 1);
					}
				}
			}
			writeAndFlush(prefix+"bottomRightRounded.png", bottomRightRounded);
			g.dispose();

			BufferedImage bottomLeftRounded = copyImage(original);
			g = createGraphic(bottomLeftRounded);
			for (int x = 1; x < 51; x++) {
				for (int y = 1; y < 51; y++) {
					//
					if (Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(51 - y, 2))) > 50.0) {
						g.fillRect(x - 1, y - 1, 1, 1);
					}
				}
			}
			writeAndFlush(prefix+"bottomLeftRounded.png", bottomLeftRounded);
			g.dispose();

			BufferedImage topLeftStraight = copyImage(original);
			g = createGraphic(topLeftStraight);
			int xpoints[] = { 0, 50, 50 };
			int ypoints[] = { 50, 50, 0 };
			g.fillPolygon(xpoints, ypoints, 3);
			writeAndFlush(prefix+"topLeftStraight.png", topLeftStraight);
			g.dispose();

			BufferedImage topRightStraight = copyImage(original);
			g = createGraphic(topRightStraight);
			int xpoints2[] = { 0, 0, 50 };
			int ypoints2[] = { 0, 50, 50 };
			g.fillPolygon(xpoints2, ypoints2, 3);
			writeAndFlush(prefix+"topRightStraight.png", topRightStraight);
			g.dispose();

			BufferedImage bottomRightStraight = copyImage(original);
			g = createGraphic(bottomRightStraight);
			int xpoints3[] = { 0, 0, 49 };
			int ypoints3[] = { 0, 49, 0 };
			g.fillPolygon(xpoints3, ypoints3, 3);
			writeAndFlush(prefix+"bottomRightStraight.png", bottomRightStraight);
			g.dispose();

			BufferedImage bottomLeftStraight = copyImage(original);
			g = createGraphic(bottomLeftStraight);
			int xpoints4[] = { 1, 50, 50 };
			int ypoints4[] = { 0, 49, 0 };
			g.fillPolygon(xpoints4, ypoints4, 3);
			writeAndFlush(prefix+"bottomLeftStraight.png", bottomLeftStraight);
			g.dispose();

			BufferedImage borderLeft = copyImage(original);
			g = createGraphic(borderLeft);
			writeAndFlush(prefix+"borderLeft.png", borderLeft);
			g.dispose();

			BufferedImage borderRight = copyImage(original);
			g = createGraphic(borderRight);
			writeAndFlush(prefix+"borderRight.png", borderRight);
			g.dispose();


			BufferedImage borderBottom = copyImage(original);
			g = createGraphic(borderBottom);
			writeAndFlush(prefix+"borderBottom.png", borderBottom);
			g.dispose();

			BufferedImage borderTop = copyImage(original);
			g = createGraphic(borderTop);
			writeAndFlush(prefix+"borderTop.png", borderTop);
			g.dispose();

		}

	}