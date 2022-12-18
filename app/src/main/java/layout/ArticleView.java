package layout;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.ArticleAdapter;
import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.Article;
import cd.sklservices.com.Beststockage.Classes.Stock;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.CategorieViewModel;

public class ArticleView extends Fragment {

    private ArticleViewModel articleViewModel ;
    private CategorieViewModel categorieViewModel ;

    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_articles_count;
    SearchView sv_articles_filter;
    ListView lvArticles;
    ArticleAdapter adapterArticle ;
    List<Article> lesArticles=new ArrayList<>();


    Map<Article,List<Stock>> map;
    String  index ;
    int count_all;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Article");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            rootView=inflater.inflate(R.layout.fragment_article, container,false);
            adapterArticle =new ArticleAdapter(getContext(), lesArticles,true);

            LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            footer= li.inflate(R.layout.footer_view,null);
            myHandler=new MyHandler();
            init();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    public void init() {

        this.categorieViewModel = new ViewModelProvider(this).get(CategorieViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;

        lvArticles =rootView.findViewById(R.id.lv_articles);
        tv_articles_count =(TextView)rootView.findViewById(R.id.tv_articles_count);
        sv_articles_filter =(SearchView) rootView.findViewById(R.id.sv_articles_filter);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_article);
        count_all=articleViewModel.count();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lesArticles.clear();
                init();
                refresh_end() ;

            }

        });

        lvArticles.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {



            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //  Toast.makeText(getActivity(), "111 3333 " , Toast.LENGTH_SHORT).show();
            }
        });


        sv_articles_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Article> result=new ArrayList<>();
                for (Article x:lesArticles){
                    if ((x.getFournisseur_id().toLowerCase()+" "+x.getDesignation().toLowerCase()).contains(newText.toLowerCase()))
                    {
                        result.add(x);
                    }
                }

               adapterArticle.update(result,swipeRefreshLayout);
                count_element_search();
                return false;
            }
        });

        fillData();

        adapterArticle =new ArticleAdapter(getContext(), lesArticles,true);
        lvArticles.setAdapter(adapterArticle);

    }

    private void fillData(){

        articleViewModel.getLoading(lesArticles) ;

        map = new HashMap<>();
        map.clear();

        for (int k = 0; k< lesArticles.size(); k++){

            map.put(lesArticles.get(k), null);
            index = lesArticles.get(k).getDesignation() ;

        }

        count_element();
        isLoading=true;
        Thread thread=new ThreadGetMoreData();
        thread.start();

        swipeRefreshLayout.setRefreshing(false);

    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //inserer le view de loading pendant la recherche
                    lvArticles.addFooterView(footer);
                    break;
                case 1:
                    //update data adapter and UI
                    adapterArticle.addItem((List <Article>)msg.obj);
                    lvArticles.removeFooterView(footer);
                    isLoading=false;
                    break;
                default:
                    break;
            }

        }
    }

    public class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            List<Article> instances =new ArrayList<>();
                    articleViewModel.getLoading2(index,instances);

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg=myHandler.obtainMessage(1,instances);
            myHandler.sendMessage(msg);
            count_element();

        }
    }


    private  void count_element() {
        tv_articles_count.setText("Articles ("+count_all+"/"+count_all+")");
        swipeRefreshLayout.setRefreshing(false);
    }

    private  void count_element_search() {
        tv_articles_count.setText("Articles ("+lvArticles.getCount()+"/"+count_all+")");
        swipeRefreshLayout.setRefreshing(false);
    }

    public static void refresh_end()
    {
        swipeRefreshLayout.setRefreshing(false);
    }

}
