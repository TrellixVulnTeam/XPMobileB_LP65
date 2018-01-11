package br.senai.sp.informatica.listadecontatos.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.senai.sp.informatica.listadecontatos.R;
import br.senai.sp.informatica.listadecontatos.model.Contato;
import br.senai.sp.informatica.listadecontatos.model.ContatoDao;

/**
 * Created by pena on 31/10/2017.
 */

class ContatoAdapter extends BaseAdapter {
    private ContatoDao dao = ContatoDao.manager;
    private Map<Integer, Long> mapa;

    public ContatoAdapter() {
        criaMapa();
    }

    @Override
    public void notifyDataSetChanged() {
        criaMapa();
        super.notifyDataSetChanged();
    }

    private void criaMapa() {
        // Cria o mapa de associação de linha:id
        mapa = new HashMap<>();
        // Obtem a lista de Objetos do DAO
        List<Contato> lista =  dao.getLista();

        // Associa o nº da linha com o id do Jogo
        for(int linha = 0;linha < lista.size();linha++) {
            Contato jogo = lista.get(linha);
            mapa.put(linha, jogo.getId());
        }
    }

    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return dao.getContato((long)id);
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View view, ViewGroup viewGroup) {

        ConstraintLayout layout;
        if(view == null) {
            // Obtem o contexto de execução do ListView
            Context ctx = viewGroup.getContext();
            // localizar o serviço de construção do layout
            LayoutInflater inflater =
                    (LayoutInflater)ctx
                            .getSystemService(
                                    Context.LAYOUT_INFLATER_SERVICE);
            // Criar um objeto de Layout
            layout = new ConstraintLayout(ctx);
            // informar o layout xml a ser carregado
            inflater.inflate(R.layout.detalhe_layout, layout);
        } else {
            layout = (ConstraintLayout)view;
        }

        // o registro da posição solicitada e encontrar o objeto
        // atribuir o objeto ao layout
        TextView tvNome = (TextView)layout.findViewById(R.id.tvNome);
        TextView tvEmail = (TextView)layout.findViewById(R.id.tvEmail);

        Long id = mapa.get(linha);
        Contato jogo = dao.getContato(id);

        tvNome.setText(jogo.getNome());
        tvEmail.setText(jogo.getEmail());

        return layout;
    }
}
