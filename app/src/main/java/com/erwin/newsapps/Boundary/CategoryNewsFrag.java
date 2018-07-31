package com.erwin.newsapps.Boundary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erwin.newsapps.Adapter.CategoryAdapter;
import com.erwin.newsapps.Adapter.ListAdapterss;
import com.erwin.newsapps.Model.Model;
import com.erwin.newsapps.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoryNewsFrag  extends Fragment {
    ListAdapterss listAdapterss;
    List<Model> list =  new ArrayList<>();
    private static final String TAG = null;
    ArrayList<String> titles =  new ArrayList<>();
    ArrayList<String> titless =  new ArrayList<>();
    ArrayList<String> description =  new ArrayList<>();
    ArrayList<String> descriptions =  new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> category_item = new ArrayList<>();
    Context context=this.getContext();
    RecyclerView recyclerView;
    String value;


    public CategoryNewsFrag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_news, container, false);
        recyclerView = view.findViewById(R.id.recyclercat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Bundle bundle =  getArguments();
        value = bundle.getString("kategori");
        System.out.println("coy3");
        System.out.println(value);

        new CategoryNewsFrag.ProcessInBackground().execute();
        return view;
    }


    public InputStream getStream(URL url){
        try {
            return  url.openConnection().getInputStream();
        } catch (IOException e){
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        //        ProgressDialog progressDialog = new ProgressDialog(context);
        Exception exception = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //progressDialog.setMessage("Busy loading rss feed...please wait...");
            //progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                String kategori="";
                URL url = new URL("http://sumeks.co.id/feed/");

                //creates new instance of PullParserFactory that can be used to create XML pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by this factory will provide support
                //for XML namespaces
                factory.setNamespaceAware(false);

                //creates a new instance of a XML pull parser using the currently configured
                //factory features
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getStream(url), "UTF_8");

                /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
                 * We should take into consideration that the rss feed name is also enclosed in a "<title>" tag.
                 * Every feed begins with these lines: "<channel><title>Feed_Name</title> etc."
                 * We should skip the "<title>" tag which is a child of "<channel>" tag,
                 * and take into consideration only the "<title>" tag which is a child of the "<item>" tag
                 *
                 * In order to achieve this, we will make use of a boolean variable called "insideItem".
                 */
                boolean insideItem = false;
                boolean check_titles=false;
                // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
                int eventType = xpp.getEventType(); //loop control variable

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG)
                    {

                        //if the tag is called "item"
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;

                        }
                        //if the tag is called "title"
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <title> and </title>

                                titles.add(xpp.nextText());
                                kategori="";

                            }
                        }else if (xpp.getName().equalsIgnoreCase("content:encoded")){
                            if (insideItem)
                            {
                                description.add(xpp.nextText());
                            }

                        }else if (xpp.getName().equalsIgnoreCase("category")){
                            String kategori_uml="";
                            if (insideItem)
                            {
                                kategori_uml=xpp.nextText();
                                kategori=kategori_uml+" "+kategori;


                                if(!category.contains(kategori_uml)){
                                    category.add(kategori_uml);
                                }
                            }
                            System.out.println("kategoril_uml");
                            System.out.println(kategori_uml);
                        }
                        //if the tag is called "link"
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <link> and </link>
                                date.add(xpp.nextText());
                            }
                        }

                    }
                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                        category_item.add(kategori.trim());

                    }

                    eventType = xpp.next(); //move to next element


                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }
        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, category);
            System.out.println("coy");
            System.out.println(category);
            System.out.println(category_item);
            System.out.println(titles);
            //Integer.valueOf(value)

            for (int i = 0; i<titles.size(); i++) {
                if (category_item.get(i).contains(value)) {
                    titless.add(titles.get(i));
                    dates.add(date.get(i));
                    descriptions.add(description.get(i));
                    }
                System.out.println(category_item.get(i).contains(value));
                }
                 System.out.println(titless);

                for (int j = 0; j<titless.size(); j++ ){
                        Model model=new Model();
                        model.setTitle(titless.get(j));
                        model.setDate(dates.get(j));
                        model.setDeskripsi(descriptions.get(j));
                        list.add(model);
                    }


            //for(int i=0; i<titles_put.size(); i++){
//            if(category_item.contains(categori)){
//                titles.add(titles_put.get(i));
//                description.add(description_put.get(i));
//            }
//        }
            listAdapterss = new ListAdapterss(list);
            recyclerView.setAdapter(listAdapterss);
        }
    }
}



