package pxb.android.axml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


public class UpdateAxml {
    // @Test
    // public void test() throws Exception {
    // a(new File("src/test/resources/a.axml"), new File("target/a-debug.axml"));
    // }

    public static void main(String... args) throws Exception {
        if (args.length < 2) {
            System.err.println("Arguments error!");
            return;
        }
        new UpdateAxml().a(new File(args[0]), new File(args[1]));
    }

    void a(File a, File b) throws Exception {
        InputStream is = new FileInputStream(a);
        byte[] xml = new byte[is.available()];
        is.read(xml);
        is.close();

        AxmlReader rd = new AxmlReader(xml);
        AxmlWriter wr = new AxmlWriter();
        rd.accept(new AxmlVisitor(wr) {

        	public NodeVisitor visitFirst(String ns, String name)      //manifest  
            {  
              return new NodeVisitor(super.visitFirst(ns, name))  
              {  
                public NodeVisitor visitChild(String ns, String name)  //manifest's child nodes  
                {  
                    if (name.equalsIgnoreCase("application")) {               //we got application node  
                        return new NodeVisitor(super.visitChild(ns, name)) {  

//******************************************添加一个meta-data标签
                       public NodeVisitor visitChild(String ns,String name){
                    	   if(name.equalsIgnoreCase("meta-data")){
                    		   return new NodeVisitor(super.visitChild(ns, name)) { 
                    			   public void visitEnd()  
                                   {  
                                     super.visitContentAttr("http://schemas.android.com/apk/res/android", "name", 0x01010003 ,   
                                             TYPE_STRING, "APPLICATION_CLASS_NAME"); 
                                     super.visitContentAttr("http://schemas.android.com/apk/res/android", "value", 0x01010024,
                                     		TYPE_STRING, "android.app.Application");
                                     super.visitEnd();  
                                   }  
                    		   };
                    	   }
                    	   return super.visitChild(ns, name);  
                    	   }	
 
//********************************************
                            
                          public void visitEnd()  
                          {  
                        	  super.visitContentAttr("http://schemas.android.com/apk/res/android", "name", 0x01010003 ,   
                                      TYPE_STRING, "com.softsec.zr.MyApplication");  
                            super.visitEnd();  
                          }  

                        };  
                    }  
                      
                    return super.visitChild(ns, name);  
                }  
              };  
            }  

        });
        byte[] modified = wr.toByteArray();
        FileOutputStream fos = new FileOutputStream(b);
        fos.write(modified);
        fos.close();
    }
}
