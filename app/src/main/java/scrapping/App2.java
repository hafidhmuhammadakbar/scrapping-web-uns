package scrapping;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App2 implements Runnable{
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

    @Override
    public void run() {

        String namaThread = Thread.currentThread().getName();
		System.out.println("Nama Thread: " + namaThread);
        int i = 0;
        try {
            Document doc = Jsoup.connect("https://uns.ac.id/id/category/uns-update/page/2").get();
            
            Elements media = doc.select("[src]");
            for (Element src : media) {
                // kalau tipenya img / gambar akan diunduh
                if (src.normalName().equals("img")) {
                    // print url gambar
                    System.out.println(src.attr("abs:src")); 
                    // panggil method untuk mengunduh gambar
                    // ganti /tmp dengan alamat folder untuk menyimpan gambar yang diunduh 
                    downloadUsingStream(src.attr("abs:src"), 
                    "/home/adztrz/Scrapp/"+"/"+src.attr("abs:src") //saya ganti ke direktori saya
                    .replace("https://", "")
                    .replace("/", "_"));
                    i++;   //Tambahan
                    System.out.println(namaThread + ", melakukan download ke-" + i); //Tambahan
                    
                }
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    


    }
    
}
