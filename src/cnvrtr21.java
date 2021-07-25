/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import java.awt.Graphics2D;

import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

public class cnvrtr21 extends JFrame
{
  String outpath = "";
  private BufferedImage gridImage;
  String fullpath;
  
 
  

  private JFileChooser fchooser;
  
  private JButton jButton1;
  
  public static void resize(String inputImagePath,
          String outputImagePath, int scaledWidth, int scaledHeight)
          throws IOException {
      // reads input image
      File inputFile = new File(inputImagePath);
      BufferedImage inputImage = ImageIO.read(inputFile);

      // creates output image
      BufferedImage outputImage = new BufferedImage(scaledWidth,
              scaledHeight, inputImage.getType());

      // scales the input image to the output image
      Graphics2D g2d = outputImage.createGraphics();
      g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
      g2d.dispose();

      // extracts extension of output file
      String formatName = outputImagePath.substring(outputImagePath
              .lastIndexOf(".") + 1);

      // writes to output file
      ImageIO.write(outputImage, formatName, new File(outputImagePath));
  }
  
  private void saveGridImage(File output, int dpii,int flag)
    throws java.io.IOException
  {
    output.delete();
    
    try
    {
      SeekableStream s = new FileSeekableStream(fullpath);
      TIFFDecodeParam param = null;
      ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
      RenderedImage op = dec.decodeAsRenderedImage(0);
      
      FileOutputStream fos = new FileOutputStream(outpath);Throwable localThrowable6 = null;
      try { JPEGEncodeParam jpgparam = new JPEGEncodeParam();
        jpgparam.setQuality(67.0F);
        ImageEncoder en = ImageCodec.createImageEncoder("jpeg", fos, jpgparam);
        en.encode(op);
        fos.flush();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable6 = localThrowable1;throw localThrowable1;
        
      }
      finally
      {

        if (fos != null) if (localThrowable6 != null) try { fos.close(); } catch (Throwable localThrowable2) { localThrowable6.addSuppressed(localThrowable2); } else fos.close();
      }
    }
    catch (Exception ex) {
      Logger.getLogger(cnvrtr21.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    
    if(dpii==300)
    	resize(outpath, outpath, 1004, 1516);
    
   
  //  System.out.println("started");
////compression starts
//    Iterator iter=ImageIO.getImageWritersByFormatName("jpeg");
//    ImageWriter writer1=(ImageWriter)iter.next();
//    ImageWriteParam iwp=writer1.getDefaultWriteParam();
//    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//    iwp.setCompressionQuality(0.1f);
//    FileImageOutputStream output1=new FileImageOutputStream(new File(outpath));
//    writer1.setOutput(output1);
//
//
//BufferedImage in = ImageIO.read(new File(outpath));
//
//BufferedImage newImage = new BufferedImage(
//    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
//    IIOImage outimage=new IIOImage(newImage,null,null);
//    writer1.write(null,outimage,iwp);
//    writer1.dispose();
//   //compression end

     
    try
    {
      BufferedImage bufferedImage = ImageIO.read(new File(outpath));
      BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 1);
      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.white, null);
      
      ImageIO.write(newBufferedImage, "png", new File(outpath.substring(0, outpath.lastIndexOf(".")+1).concat("png")));
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(null, ex);
    }
    

    gridImage = ImageIO.read(new File(outpath.substring(0,  outpath.lastIndexOf(".")+1).concat("png")));
    


    String formatName = "png";
    
    for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName("png"); iw.hasNext();) {
      ImageWriter writer = (ImageWriter)iw.next();
      javax.imageio.ImageWriteParam writeParam = writer.getDefaultWriteParam();
      ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(1);
      IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
      if ((!metadata.isReadOnly()) && (metadata.isStandardMetadataFormatSupported()))
      {

    	
        setDPI(metadata, dpii);
        try {
          ImageOutputStream stream = ImageIO.createImageOutputStream(output);Throwable localThrowable7 = null;
          try { writer.setOutput(stream);
            writer.write(metadata, new javax.imageio.IIOImage(gridImage, null, metadata), writeParam);
          }
          catch (Throwable localThrowable4)
          {
            localThrowable7 = localThrowable4;throw localThrowable4;
          }
          finally {
            if (stream != null) if (localThrowable7 != null) try { stream.close(); } catch (Throwable localThrowable5) { localThrowable7.addSuppressed(localThrowable5); } else stream.close(); } } finally { File f;
          File f1 = new File(outpath.substring(0,  outpath.lastIndexOf(".")+1).concat("png"));
          f1.delete();
       //   JOptionPane.showMessageDialog(null, "File Successfully Converted.");
         
        }
      }
    }
    
    if(dpii==300)
    {compressImage(outpath, outpath);
    }
    
  }
  
  private void setDPI(IIOMetadata metadata, int dp)
    throws IIOInvalidTreeException
  { 
    double dotsPerMilli = dp / 100 * 100 / 10 / 2.54D;
    
    IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
    horiz.setAttribute("value", Double.toString(dotsPerMilli));
    
    IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
    vert.setAttribute("value", Double.toString(dotsPerMilli));
    
    IIOMetadataNode dim = new IIOMetadataNode("Dimension");
    dim.appendChild(horiz);
    dim.appendChild(vert);
    
    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
    root.appendChild(dim);
    
    metadata.mergeTree("javax_imageio_1.0", root);
  }
  


  
 
  private JLabel jLabel1;
private  JTextArea textArea;


  
   File[] files;
   File fil;
  
  private void jButton1ActionPerformed(ActionEvent evt)
  {
       textArea.setText("");
              textArea.update(textArea.getGraphics());
       fil=fchooser.getSelectedFile();
  //    JOptionPane.showMessageDialog(null,fil.getName());
       files=fil.listFiles(new FilenameFilter() { 
                 public boolean accept(File dir, String filename)
                      { if((filename.endsWith(".tiff"))||(filename.endsWith(".TIF"))||(filename.endsWith(".TIFF"))||(filename.endsWith(".tif")))
                              return true;
                      else
                          return false;
                      }
        } );
      if(files.length>0)
      { foldermkr(300);
       textArea.setText("");
              textArea.update(textArea.getGraphics());
       foldermkr(600);
       
        textArea.setText("***All files successfully converted***");
              textArea.update(textArea.getGraphics());
      }
      else
      {
           textArea.setText(" No tiff file found,\n At specified location.");
              textArea.update(textArea.getGraphics());
      }

  }
  
  public void foldermkr(int dpi){
      int fexist=0,nmchng=0;
  File  theDir;
// if the directory does not exist, create it
  
do {
    if(nmchng==0)
     theDir = new File(fchooser.getCurrentDirectory().getPath().concat("\\"+fil.getName()+" "+dpi));
    else
         theDir = new File(fchooser.getCurrentDirectory().getPath().concat("\\"+fil.getName()+" "+dpi+"("+nmchng+")"));
    if(!theDir.exists()){
  textArea.append("Creating Directory: '" + theDir.getName()+"'\n");
 textArea.update(textArea.getGraphics());
    boolean result = false;

    try{
        theDir.mkdir();
        result = true;
    } 
    catch(SecurityException se){
        //handle it
    }        
    if(result) {    
        textArea.append("Directory created\n");  
        fexist=1;
         textArea.update(textArea.getGraphics());
    }
    }
    nmchng++;
}while (fexist!=1);

      int tot=files.length;
      int cnt=1;
      String temps=textArea.getText();
      for(File allfile:files){
      fullpath= allfile.getPath();
       outpath=theDir.getPath().concat("\\" + allfile.getName().substring(0,allfile.getName().lastIndexOf('.') ) + ".jpg");
       
       File tempimg=new File(outpath);
          try {
        	  if(dpi==300)
        	  { saveGridImage(tempimg, dpi,1);
        	 
        	  }
        	  else
        		  saveGridImage(tempimg, dpi,2);
             
              
              
          textArea.setText(temps+"Converting "+cnt+"/"+tot+"  Done...\n");
           
        textArea.update(textArea.getGraphics());
          textArea.setCaretPosition(textArea.getDocument().getLength());
              
          } catch (IOException ex) {
              Logger.getLogger(cnvrtr21.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          cnt++;
      }
  }
  JScrollPane scrollPane ;
  
 public void load_gui(){
	
     fchooser = new JFileChooser();
  
    jLabel1 = new JLabel();
    JFrame jf=new JFrame();
    jf.setLayout(new FlowLayout());
    jButton1 = new JButton();
     textArea = new JTextArea(3, 40);
     textArea.setText("Genterated Log...");
     textArea.setForeground(Color.GRAY);
     scrollPane = new JScrollPane(textArea); 
    textArea.setEditable(false);
     JPanel jb=new JPanel();
    fchooser.setDialogType(2);
    fchooser.setControlButtonsAreShown(false);
  //  fchooser.setCurrentDirectory(new File("C:\\Users\\Anupma\\Desktop"));
    
     fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    jLabel1.setFont(new Font("Tahoma", 1, 24));
  jLabel1.setForeground(Color.DARK_GRAY);
    jLabel1.setText("TIFF to JPEG & DPI Setter");
   
    
    jButton1.setFont(new Font("Tahoma", 1, 14));
    jButton1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    jButton1.setForeground(new Color(51, 102, 255));
    jButton1.setText("Convert");
    
    jButton1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        cnvrtr21.this.jButton1ActionPerformed(evt);
      }
      
     
      
    });
    jb.add(jButton1);
    jb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
    jb.setOpaque(false);
jf.getContentPane().setBackground(Color.white );  
    jf.add(jLabel1);
    jf.add(fchooser);
    jf.setResizable(false);
     jf.add(jb);
    jf.add(scrollPane);
     jf.setSize(550, 550);
     Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
jf.setLocation(dim.width/2-jf.getSize().width/2, dim.height/2-jf.getSize().height/2-50);
     jf.setVisible(true);

 }
 
  







  public static void main(String[] args)
  { 
      try {
          /*  try {
                  for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                      if ("Nimbus".equals(info.getName())) {
                          javax.swing.UIManager.setLookAndFeel(info.getClassName());
                          break;
                      }
                  }
              }catch(Exception e){
                      
              }
            */
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException ex) {
          Logger.getLogger(cnvrtr21.class.getName()).log(Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
          Logger.getLogger(cnvrtr21.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
          Logger.getLogger(cnvrtr21.class.getName()).log(Level.SEVERE, null, ex);
      } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(cnvrtr21.class.getName()).log(Level.SEVERE, null, ex);
      }
          
    
          new cnvrtr21().load_gui();

  }
  
	public static void compressImage(String in,String out1) throws FileNotFoundException, IOException{
		   float f=0.9f;;
		  File input = new File(in);
	        BufferedImage image = ImageIO.read(input);
	        ImageOutputStream ios;
	        ImageWriter writer;
	        File output = new File(out1);
	        
	     
	   do{   OutputStream out = new FileOutputStream(output);
		   writer =  ImageIO.getImageWritersByFormatName("jpg").next();
	         ios = ImageIO.createImageOutputStream(out);
	        writer.setOutput(ios);

	        ImageWriteParam param = writer.getDefaultWriteParam();
	        if (param.canWriteCompressed()){
	            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	            param.setCompressionQuality(f);
	        }

	        writer.write(null, new IIOImage(image, null, null), param);  
	        out.close();
	        ios.close();
	        writer.dispose();
	        f-=0.05;
	       
	   }while((output.length()>307200)&&(f>0.10f));  
	      
	  
	 
	        
	}
	
	
}

