package com.moonica.fdm.model;

import com.moonica.fdm.R;

import java.util.ArrayList;
import java.util.Collections;

public class FactoryFileFinti {
    private static FactoryFileFinti singleton;

    public static FactoryFileFinti getInstance() {
        if (singleton == null)
            singleton = new FactoryFileFinti();
        return singleton;
    }

    private ArrayList<FileFinto> listaFile = new ArrayList<>();
    private ArrayList<FileFinto> listaAvatar = new ArrayList<>();

    private FactoryFileFinti() {
        FileFinto f1 = new FileFinto();
        f1.setNome("mauro.png");
        f1.setImmagineId(R.drawable.mauro);
        listaAvatar.add(f1);

        FileFinto f2 = new FileFinto();
        f2.setNome("coky.png");
        f2.setImmagineId(R.drawable.coky);
        listaAvatar.add(f2);

        FileFinto f3 = new FileFinto();
        f3.setNome("lore.png");
        f3.setImmagineId(R.drawable.io);
        listaAvatar.add(f3);

        FileFinto f4 = new FileFinto();
        f4.setNome("a1.png");
        f4.setImmagineId(R.drawable.a1);
        listaAvatar.add(f4);

        FileFinto f5 = new FileFinto();
        f5.setNome("a2.png");
        f5.setImmagineId(R.drawable.a2);
        listaAvatar.add(f5);

        FileFinto f6 = new FileFinto();
        f6.setNome("a3.png");
        f6.setImmagineId(R.drawable.a3);
        listaAvatar.add(f6);

        FileFinto f7 = new FileFinto();
        f7.setNome("a4.png");
        f7.setImmagineId(R.drawable.a4);
        listaAvatar.add(f7);

        FileFinto f8 = new FileFinto();
        f8.setNome("a5.png");
        f8.setImmagineId(R.drawable.a5);
        listaAvatar.add(f8);

        FileFinto f9 = new FileFinto();
        f9.setNome("a6.png");
        f9.setImmagineId(R.drawable.a6);
        listaAvatar.add(f9);

        FileFinto f10 = new FileFinto();
        f10.setNome("appunti maggio.pdf");
        f10.setImmagineId(R.drawable.pdf_preview);

        FileFinto f11 = new FileFinto();
        f11.setNome("file esercizi terza lezione.txt");
        f11.setImmagineId(R.drawable.txt_preview);

        FileFinto f12 = new FileFinto();
        f12.setNome("Lezione 5 Novembre.pdf");
        f12.setImmagineId(R.drawable.pdf_preview);

        FileFinto f13 = new FileFinto();
        f13.setNome("Presentazione 13 Novembre.pdf");
        f13.setImmagineId(R.drawable.pdf_preview);

        FileFinto f14 = new FileFinto();
        f14.setNome("appunti.txt");
        f14.setImmagineId(R.drawable.txt_preview);

        FileFinto f15 = new FileFinto();
        f15.setNome("Blocco slide.rar");
        f15.setImmagineId(R.drawable.rar);

        FileFinto f16 = new FileFinto();
        f16.setNome("Blocco slide.rar");
        f16.setImmagineId(R.drawable.rar);

        FileFinto f17 = new FileFinto();
        f17.setNome("esercizi.rar");
        f17.setImmagineId(R.drawable.rar);

        FileFinto f18 = new FileFinto();
        f18.setNome("eserciziBash.txt");
        f18.setImmagineId(R.drawable.txt_preview);

        listaFile.add(f10);
        listaFile.add(f11);
        listaFile.add(f12);
        listaFile.add(f13);
        listaFile.add(f14);
        listaFile.add(f15);
        listaFile.add(f16);
        listaFile.add(f17);
        listaFile.add(f18);

        Collections.shuffle(listaFile);


    }

    public ArrayList<FileFinto> restituisciAvatar() {
        ArrayList<FileFinto> fff = new ArrayList<>();
        fff.addAll(listaAvatar);
        return fff;
    }

    public ArrayList<FileFinto> restituisciFile() {
        ArrayList<FileFinto> fileRestituiti = new ArrayList<>();
        fileRestituiti.addAll(listaFile);
        return fileRestituiti;
    }

    public int cercaAvatar(String nomeFile) {
        for (FileFinto fileFinto : listaAvatar)
            if (fileFinto.getNome().equals(nomeFile))
                return fileFinto.getImmagineId();
        return -1;
    }

    public int cercaFile(String nomeFile) {
        for (FileFinto f : listaFile)
            if (f.getNome() != null)
                if (f.getNome().equals(nomeFile))
                    return f.getImmagineId();
        return -1;
    }
}
