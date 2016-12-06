package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessing {
	private BufferedImage image;
	private int width;
	private int height;
	private int[][] grayImage;
	public ImageProcessing(String imageName) throws IOException{
		this.image=ImageIO.read( new File(System.getProperty("user.dir") +"\\bin\\" + imageName));
		this.width=this.image.getWidth();
		this.height=this.image.getHeight();
		grayImage=imageToGray();
	}
	public int[][] imageToGray(){
		int[][] grayImage = new int[height][width];
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  int p = image.getRGB(x,y);
				  int red = (p>>16)&0xff;
				  int green = (p>>8)&0xff;
				  int blue = p&0xff;
				  int gray=(77*red+150*green+29*blue)>>8;
				  grayImage[y][x]=gray;
			  }
			}
		return grayImage;
	}
	public void saveGrayImage() throws IOException{
		BufferedImage pilt=image;
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  Color color=new Color(grayImage[y][x],grayImage[y][x],grayImage[y][x]);
				  pilt.setRGB(x, y, color.getRGB());
			  }
		}
		File ouptut = new File(System.getProperty("user.dir") + "\\bin\\grad_angle.jpg");
        ImageIO.write(pilt, "jpg", ouptut);
	}
	public void gaussianFilter(){
		int[][] tmpImage = new int[height][width];
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  int summa=0;
				  int counter=0;
				  for(int i=Math.min(0, Math.max(-y, -1)); i<Math.min(2, height-y); ++i){
					  for(int j=Math.min(0, Math.max(-x, -1)); j<Math.min(2, width-x);++j){
						  summa+=grayImage[y+i][x+j]*(2-Math.abs(i))*(2-Math.abs(j));
						  counter+=(2-Math.abs(i))*(2-Math.abs(j));
					  }
				  }
				  tmpImage[y][x]=summa/counter;
			  }
		}
		this.grayImage=tmpImage;
	}
	public void sobelFilter(){
		int[][] tmpImage = new int[height][width];
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  int grad_y=0;
				  int grad_x=0;
				  for(int i=Math.min(0, Math.max(-y, -1)); i<Math.min(2, height-y); ++i){
					  for(int j=Math.min(0, Math.max(-x, -1)); j<Math.min(2, width-x);++j){
						  grad_y+=grayImage[y+i][x+j]*(2-Math.abs(i))*(j)/8;
						  grad_x+=grayImage[y+i][x+j]*(2-Math.abs(j))*i/8;
					  }
				  }
				  //tmpImage[y][x]=(int)(127.5+Math.atan(grad_y/(grad_x+0.001))*2 / Math.PI*127.5);
				  //tmpImage[y][x]=(int)(127.5+grad_x);
				  tmpImage[y][x]=(int)(Math.sqrt(grad_y*grad_y+grad_x+grad_x));
			  }
		}
		this.grayImage=tmpImage;
	}
}
