/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package scrapping;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App extends Thread {
    // alamat web yang akan diuduh gambarnya
    String urlWeb;

    public App(String urlWeb){ 
        this.urlWeb = urlWeb;
    }

    public void run() {
        String namaThread = Thread.currentThread().getName();
		System.out.println("Nama Thread: " + namaThread);
        int i=0; 

        try {
            Document doc = Jsoup.connect(this.urlWeb/*"https://uns.ac.id/id/category/uns-update"*/).get();
            
            Elements media = doc.select("[src]");
            for (Element src : media) {
                // kalau tipenya img gambar akan diunduh
                if (src.normalName().equals("img")) {
                    // print url gambar
                    System.out.println(src.attr("abs:src"));
                    
                    // panggil method untuk mengunduh gambar
                    // ganti /tmp dengan alamat folder untuk menyimpan gambar yang diunduh 
                    
                    // string isi dengan nama dari file
                    String namaFile = src.attr("abs:src").replace("https://", "").replace("/", "_");
                    String namaFileFix = namaFile;
                    // lakukan pengecekan karena saat nama file tidak berakhir dengan .png .jpg .jpeg maka akan 
                    //      error exception
                    for (int a = 0; a < namaFile.length(); a++) {
                        char ch = namaFile.charAt(a);
                        if(ch == '?'){
                            // hapus karakter setelah tanda '?'
                            namaFileFix = namaFile.substring(0, namaFile.indexOf("?"));
                        }
                    }

                    // otomatis mengatur direktori user saat ini
                    String namaDirektori = System.getProperty("user.dir").replace("//", "//");

                    downloadUsingStream(src.attr("abs:src"),namaDirektori+"\\"+namaFileFix);
                    
                    i++; //Tambahan
                    System.out.println(namaThread + ", melakukan download ke-" + i); //Tambahan
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // method untuk mendownload file
    // String urlStr : alamat url file
    // String file : path lokasi file hasil download disimpan
    // diambil dari https://www.digitalocean.com/community/tutorials/java-download-file-url
    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    public static void main(String[] args) {
        App app1 = new App("https://uns.ac.id/id/category/uns-update/page/3");
        App app2 = new App("https://uns.ac.id/id/category/uns-update/page/4");

        app1.start();
        app2.start();
    }
}
