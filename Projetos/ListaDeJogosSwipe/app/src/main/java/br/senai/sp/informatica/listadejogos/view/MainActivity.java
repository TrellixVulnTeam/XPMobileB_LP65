package br.senai.sp.informatica.listadejogos.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import br.senai.sp.informatica.listadejogos.R;
import br.senai.sp.informatica.listadejogos.model.JogoDao;

public class MainActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener {
    private JogoDao dao = JogoDao.manager;
    private ListView listView;
    private MenuItem itEditar;
    private MenuItem itApagar;
    private JogoAdapter itemLista;
    // Identificação para a chamada a Activity EditarCadastrar
    private final int EDITA_JOGO = 0;
    private final int NOVO_JOGO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cria o Adapter para que ele forneça os dados para o ListView
        itemLista = new JogoAdapter();

        listView = (ListView)findViewById(R.id.listView);
        // Registra o adapter no ListView
        listView.setAdapter(itemLista);
        // Solicita ao listView que qualquer click em um item do listView
        // será redirecionado ao médoto "onItemClick" desta classe (MainActivity)
        listView.setOnItemClickListener(this);
    }

    /*
        Este método trata da ação do click nos itens da lista
    */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int linha, long id) {
        // é criado um Intent para definir ao Android qual Activity será chamada
        Intent tela = new Intent(getBaseContext(), EditarCadastrar.class);
        // é passado o ID do objeto para a nova Activity a fim de informar
        // qual Jogo deverá ser editado
        tela.putExtra("id", id);
        // é solicitado ao Android que seja iniciada a execução de uma nova Activity
        // porém também é solicitado informar quando esta activity retornar
        startActivityForResult(tela, EDITA_JOGO);
    }

    /*
        Este método trata da ação de inclusão de um novo Jogo
     */
    public void adicionaJogo(View view) {
        // é criado um Intent para definir ao Android qual Activity será chamada
        Intent tela = new Intent(getBaseContext(), EditarCadastrar.class);
        // é solicitado ao Android que seja iniciada a execução de uma nova Activity
        // porém também é solicitado informar quando esta activity retornar
        startActivityForResult(tela, NOVO_JOGO);
    }

    /*
        Este método trata da ação do retorno de uma Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String msg = "A " +
                (requestCode == EDITA_JOGO ? "alteração" : "inclusão") +
                " do Jogo foi ";

        // Se a activity informar que houve sucesso em sua execução
        if(resultCode == RESULT_OK) {
            // é solicitado ao Adapter que os itens da lista sejam atualizados
            // no listView
            itemLista.notifyDataSetChanged();
            msg += "um sucesso";
        } else {
            msg += "cancelada";
        }

        Snackbar.make(findViewById(R.id.main_layout), msg, Snackbar.LENGTH_LONG).show();
    }
}
