package com.moonica.fdm.model;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import com.moonica.fdm.R;
import java.util.ArrayList;

public class ModificaSezioneAdapter extends RecyclerView.Adapter<ModificaSezioneAdapter.ModificaSezioneViewHolder> {

    ArrayList<Sezione> lista = new ArrayList<>();
    ArrayList<Contenuto> listaContenuti = new ArrayList<>();
    Corso corso = new Corso();

    public static class ModificaSezioneViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titoloSezione;
        ImageView elimina;
        RelativeLayout attivatore;
        LinearLayout vistaContenuti;

        public ModificaSezioneViewHolder(@NonNull final View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view_sezioni);
            titoloSezione = itemView.findViewById(R.id.titolo_sezione);
            elimina = itemView.findViewById(R.id.freccia);
            attivatore = itemView.findViewById(R.id.attivatore);
            vistaContenuti = itemView.findViewById(R.id.vistaContenuti);
        }
    }

    public ModificaSezioneAdapter(ArrayList<Sezione> lista, Corso corso){
        this.lista = lista;
        this.corso = corso;
    }

    @Override
    public ModificaSezioneViewHolder onCreateViewHolder(ViewGroup group, int i){
        View v = LayoutInflater.from(group.getContext()).inflate(R.layout.activity_item_sezione, group, false);
        ModificaSezioneViewHolder svh = new ModificaSezioneViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ModificaSezioneViewHolder sezioneViewHolder, final int i){

        FactorySezioni factorySezioni = FactorySezioni.getInstance();
        final ArrayList<Sezione> lista2 = factorySezioni.getSezioniCorso(corso.getNome());

        sezioneViewHolder.cv.setMinimumHeight(50);
        sezioneViewHolder.cv.setRadius(20.1f);
        sezioneViewHolder.titoloSezione.setText(lista.get(i).getTitolo());
        sezioneViewHolder.titoloSezione.setPadding(0, 25, 0, 25);
        sezioneViewHolder.cv.setCardBackgroundColor(0xff225599);
        sezioneViewHolder.titoloSezione.setTextColor(0xffffffff);
        sezioneViewHolder.titoloSezione.setTextSize(18);
        sezioneViewHolder.elimina.setImageResource(R.drawable.ic_delete_red_24dp);
        sezioneViewHolder.elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lista2.remove(i);
                lista.remove(i);
                notifyDataSetChanged();
                notifyItemRemoved(i);
            }
        });
        if (lista != null) {
            listaContenuti = lista.get(i).getContenuti();
            for (final Contenuto contenuto : listaContenuti) {
                Space space = new Space(OttieniContesto.getAppContext());
                space.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                ImageView icona = new ImageView(OttieniContesto.getAppContext());
                icona.setImageResource(contenuto.getIdIcona());
                TextView testo = new TextView(OttieniContesto.getAppContext());
                testo.setText(contenuto.getTesto());
                GradientDrawable border = new GradientDrawable();
                ImageButton cancella = new ImageButton(OttieniContesto.getAppContext());

                border.setColor(0xeeeeeeee);
                border.setStroke(3, 0xFF225599);
                border.setCornerRadius(20.1f);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    sezioneViewHolder.vistaContenuti.setBackgroundDrawable(border);
                } else {
                    sezioneViewHolder.vistaContenuti.setBackground(border);
                }
                icona.setLayoutParams(new LinearLayout.LayoutParams(70, 70));
                testo.setTextColor(0xFF225599);
                testo.setTextSize(18);
                testo.setPadding(20, 0, 0, 0);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_END);

                RelativeLayout padre = new RelativeLayout(OttieniContesto.getAppContext());
                LinearLayout horizontal = new LinearLayout(OttieniContesto.getAppContext());
                padre.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                horizontal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                horizontal.addView(icona);
                horizontal.addView(testo);
                horizontal.addView(space);
                horizontal.setPadding(80, 20, 0, 10);
                padre.addView(horizontal);
                sezioneViewHolder.vistaContenuti.addView(padre);
                padre.addView(cancella);
                sezioneViewHolder.vistaContenuti.setPadding(0, 10, 0, 20);
                sezioneViewHolder.vistaContenuti.setVisibility(View.GONE);
            }
        }

        sezioneViewHolder.attivatore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sezioneViewHolder.vistaContenuti.getVisibility() == View.GONE)
                    expand(sezioneViewHolder.vistaContenuti);
                else
                    collapse(sezioneViewHolder.vistaContenuti);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lista == null)
            return 0;
        else
            return lista.size();
    }

    public static void expand(final View v) {
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? targetHeight : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density)) * 4);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density)) * 4);
        v.startAnimation(a);
    }
}
