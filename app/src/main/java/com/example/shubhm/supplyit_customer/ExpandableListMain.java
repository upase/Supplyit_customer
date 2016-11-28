package com.example.shubhm.supplyit_customer;

import android.app.ExpandableListActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shubhm on 27-11-2016.
 */
public class ExpandableListMain extends ExpandableListActivity
{
    //Initialize variables
    private static final String STR_CHECKED = " has Checked!";
    private static final String STR_UNCHECKED = " has unChecked!";
    private int ParentClickStatus=-1;
    private int ChildClickStatus=-1;
    private ArrayList<Parent> parents;
    URL imageURL;
    String responseStr="" ;
    String query_result="";
    JSONArray dataJsonArr = null;
    ArrayList<String> orders=new ArrayList<String>();
    ArrayList<String> date=new ArrayList<String>();
    ArrayList<String> totalcost=new ArrayList<String>();
    ArrayList<String> shopname=new ArrayList<String>();
    ArrayList<List> Price=new ArrayList<List>();
    ArrayList<List> quantity=new ArrayList<List>();
    ArrayList<List> productname=new ArrayList<List>();
   private static final String TAG = "MyProductActivity.java";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Resources res = this.getResources();
        //  Drawable devider = res.getDrawable(R.drawable.ab_bottom_solid_mystyle);

        // Set ExpandableListView values

        getExpandableListView().setGroupIndicator(null);
        getExpandableListView().setDivider(this.getResources().getDrawable(R.drawable.transperent_color));
        getExpandableListView().setChildDivider(this.getResources().getDrawable(R.drawable.transperent_color));
        getExpandableListView().setDividerHeight(1);
        registerForContextMenu(getExpandableListView());

        //Creating static data in arraylist


        // Adding ArrayList data to ExpandableListView values

        new orderdetails().execute();
    }

    /**
     * here should come your data service implementation
     * @return
     */
    private ArrayList<Parent> buildDummyData()
    {
        // Creating ArrayList of type parent class to store parent class objects
        final ArrayList<Parent> list = new ArrayList<Parent>();

        int countorders=shopname.size();
        int countitems=4;
        for (int i = 0; i < countorders; i++)
        {
            //Create parent class object
            Parent parent = new Parent();
            int bill=Integer.parseInt(totalcost.get(i));
            String myshopname=shopname.get(i);
            String mydate=date.get(i);
            String total="TotalBill:"+bill;

            parent.setName("" + i);
            parent.setText1(myshopname);
            parent.setText2(mydate+"\t\t\t"+total);
            parent.setChildren(new ArrayList<Child>());

            // Create Child class object

           // List p=Price.get(i);
           // List q=quantity.get(i);
           // List n=productname.get(i);

            ArrayList<String> prod = new ArrayList<String>(Price.get(i));
            ArrayList<String> quant = new ArrayList<String>(quantity.get(i));
            ArrayList<String> prodname = new ArrayList<String>(productname.get(i));

            for(int j=0;j<prod.size();j++) {
                String item=prodname.get(j);
                int itemno=2;
                String listitem=item;//"Quantity:"+quant.get(j)+"\t\t\t\t\tPrice:"+prod.get(j)+"\t\t\t\t\t"+;
                Child child = new Child();
                child.setName("" + i);
                child.setText1(listitem);
                child.setText3("Quantity:"+quant.get(j));
                child.setText4("Price:"+prod.get(j));
                //Add Child class object to parent class object

                parent.getChildren().add(child);
            }
            // Set values in parent class object

            //Adding Parent class object to ArrayList
            list.add(parent);
        }
        return list;
    }


    private void loadHosts(final ArrayList<Parent> newParents)
    {
        if (newParents == null)
            return;

        parents = newParents;

        // Check for ExpandableListAdapter object
        if (this.getExpandableListAdapter() == null)
        {
            //Create ExpandableListAdapter Object
            final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter();

            // Set Adapter to ExpandableList Adapter
            this.setListAdapter(mAdapter);
        }
        else
        {
            // Refresh ExpandableListView data
            ((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
        }
    }

    /**
     * A Custom adapter to create Parent view (Used grouprow.xml) and Child View((Used childrow.xml).
     */
    private class MyExpandableListAdapter extends BaseExpandableListAdapter
    {


        private LayoutInflater inflater;

        public MyExpandableListAdapter()
        {
            // Create Layout Inflator
            inflater = LayoutInflater.from(ExpandableListMain.this);
        }


        // This Function used to inflate parent rows view

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parentView)
        {
            final Parent parent = parents.get(groupPosition);

            // Inflate grouprow.xml file for parent rows
            convertView = inflater.inflate(R.layout.grouprow, parentView, false);

            // Get grouprow.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.text1)).setText(parent.getText1());
            ((TextView) convertView.findViewById(R.id.text)).setText(parent.getText2());
          //  Toast.makeText(getApplicationContext(),parent.getText3(),Toast.LENGTH_SHORT).show();
          //  ((TextView) convertView.findViewById(R.id.text2)).setText(parent.getText3());
           // ((TextView) convertView.findViewById(R.id.text3)).setText(parent.getText4());
            ImageView image=(ImageView)convertView.findViewById(R.id.image);

            image.setImageResource(
                    getResources().getIdentifier(
                            "com.androidexample.customexpandablelist:drawable/setting"+parent.getName(),null,null));

            ImageView rightcheck=(ImageView)convertView.findViewById(R.id.rightcheck);

            //Log.i("onCheckedChanged", "isChecked: "+parent.isChecked());

            // Change right check image on parent at runtime
            if(parent.isChecked()==true){
                rightcheck.setImageResource(
                        getResources().getIdentifier(
                                "com.androidexample.customexpandablelist:drawable/rightcheck",null,null));
            }
            else{
                rightcheck.setImageResource(
                        getResources().getIdentifier(
                                "com.androidexample.customexpandablelist:drawable/button_check",null,null));
            }

            // Get grouprow.xml file checkbox elements
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            checkbox.setChecked(parent.isChecked());

            // Set CheckUpdateListener for CheckBox (see below CheckUpdateListener class)
            checkbox.setOnCheckedChangeListener(new CheckUpdateListener(parent));

            return convertView;
        }


        // This Function used to inflate Child rows view
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parentView)
        {
            final Parent parent = parents.get(groupPosition);
            final Child child = parent.getChildren().get(childPosition);

            // Inflate childrow.xml file for Child rows
            convertView = inflater.inflate(R.layout.childrow, parentView, false);

            // Get childrow.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.text1)).setText(child.getText1());
            ((TextView) convertView.findViewById(R.id.text2)).setText(child.getText3());
            ((TextView) convertView.findViewById(R.id.text3)).setText(child.getText4());

            ImageView image=(ImageView)convertView.findViewById(R.id.image);
            image.setImageResource(
                    getResources().getIdentifier(
                            "com.androidexample.customexpandablelist:drawable/setting"+parent.getName(),null,null));

            return convertView;
        }


        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
            return parents.get(groupPosition).getChildren().get(childPosition);
        }

        //Call when Child row clicked
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            /****** When Child row clicked then this function call *******/

            //Log.i("Noise", "parent == "+groupPosition+"=  Child : =="+childPosition);
            if( ChildClickStatus!=childPosition)
            {
                ChildClickStatus = childPosition;


            }

            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            int size=0;
            if(parents.get(groupPosition).getChildren()!=null)
                size = parents.get(groupPosition).getChildren().size();
            return size;
        }


        @Override
        public Object getGroup(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroup ");

            return parents.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return parents.size();
        }

        //Call when parent row clicked
        @Override
        public long getGroupId(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);

            if(groupPosition==2 && ParentClickStatus!=groupPosition){

                //Alert to user

            }

            ParentClickStatus=groupPosition;
            if(ParentClickStatus==0)
                ParentClickStatus=-1;

            return groupPosition;
        }

        @Override
        public void notifyDataSetChanged()
        {
            // Refresh List rows
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty()
        {
            return ((parents == null) || parents.isEmpty());
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }



        /******************* Checkbox Checked Change Listener ********************/

        private final class CheckUpdateListener implements OnCheckedChangeListener
        {
            private final Parent parent;

            private CheckUpdateListener(Parent parent)
            {
                this.parent = parent;
            }
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.i("onCheckedChanged", "isChecked: "+isChecked);
                parent.setChecked(isChecked);

                ((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();

                final Boolean checked = parent.isChecked();

            }
        }
        /***********************************************************************/

    }
    /**************************************************************/
    public class orderdetails extends AsyncTask<String,String,Void>
    {
        HttpEntity resEntity;
        public void onPreExecute()

        {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(String... arg)
        {

            try{

                imageURL = new URL("http://www.supplyit.comli.com/db/order_details.php");
                HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                connection.setDoInput(true);
                connection.connect();
                String postReceiverUrl=imageURL.toString();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("customerId", "1"));
                //       Log.v(TAG, "postURL: " + postReceiverUrl);
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(postReceiverUrl);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse response = httpClient.execute(httpPost);
                resEntity = response.getEntity();

                if (resEntity != null) {
                    responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v(TAG, "Response: " + responseStr);
                    System.out.println(responseStr);
                }
                else
                {
                    responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v(TAG, "Response: " + responseStr);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.v(TAG, "Response: " +  responseStr);
            String jsonStr = responseStr;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("SUCCESS")) {
                        String result1 = jsonObj.getString("result");

                        //   JSONParser jParser = new JSONParser();

                        // get json string from url
                        JSONObject json =  new JSONObject(result1);

                        // get the array of users
                        dataJsonArr = json.getJSONArray("c");
                        System.out.println(dataJsonArr);
                        ArrayList<String> pprice=new ArrayList<String>();
                        ArrayList<String> pitem=new ArrayList<String>();
                        ArrayList<String> pquantity=new ArrayList<String>();
                        String del="";
                        for (int i = 0; i < dataJsonArr.length(); i++) {
                            JSONObject c = dataJsonArr.getJSONObject(i);
                            String OrderId=c.getString("OrderId");
                            String datetime=c.getString("DateTime");
                            String total=c.getString("TotalCost");
                            String shop=c.getString("ShopName");
                            String pname=c.getString("ProductName");
                            String itemprice=c.getString("Price");
                            String itemquantity=c.getString("Quantity");

                            System.out.println(Price);
                            System.out.println(orders);
                            System.out.println(productname);
                            System.out.println(quantity);
                            System.out.println(orders);
                            System.out.println(date);
                            if(orders.isEmpty())
                            {
                                orders.add(OrderId);
                                date.add(datetime);
                                totalcost.add(total);
                                shopname.add(shop);

                                pprice.add(itemprice);
                                pitem.add(pname);
                                pquantity.add(itemquantity);
                                //    ShopDelivery.add(delivery);
                            }
                            else if(orders.contains(OrderId))
                            {
                                pprice.add(itemprice);
                                pitem.add(pname);
                                pquantity.add(itemquantity);
                            }
                            else
                            {
                                orders.add(OrderId);
                                date.add(datetime);
                                totalcost.add(total);
                                shopname.add(shop);
                                // System.out.println(category);
                                Price.add(pprice);
                                productname.add(pitem);
                                quantity.add(pquantity);
                                // System.out.println(ShopCategory);

                                pitem=new ArrayList<String>();
                                pprice=new ArrayList<String>();
                                pquantity=new ArrayList<String>();

                                pprice.add(itemprice);
                                pitem.add(pname);
                                pquantity.add(itemquantity);
                            }

                           if(i==dataJsonArr.length()-1)
                            {
                                Price.add(pprice);
                                productname.add(pitem);
                                quantity.add(pquantity);
                            }
                        }


                        final ArrayList<Parent> dummyList = buildDummyData();
                        loadHosts(dummyList);
                        //  responseStr=result1;
                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(getApplicationContext(), "Data could not be inserted. Signin failed.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
            //    new pmatch().execute(responseStr);

        }
    }
}
