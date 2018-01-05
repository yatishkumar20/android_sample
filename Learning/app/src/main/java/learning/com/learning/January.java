package learning.com.learning;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yatish on 6/3/17.
 */

public class January extends Fragment{

    ArrayList instdApp ;
    RecyclerView ins_apps;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_january, container, false);


        /*Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = getContext().getPackageManager().queryIntentActivities( mainIntent, 0);

        for(int i=0;i<pkgAppsList.size();i++){

            String appName = pkgAppsList.get(i).toString();

            Log.d("Installed Applications", appName+" "+i);


        }
*/
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        List<ApplicationInfo> installedApps = new ArrayList<ApplicationInfo>();

        instdApp = new ArrayList<>();

        for(ApplicationInfo app : apps) {
            //checks for flags; if flagged, check if updated system app
            if((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                installedApps.add(app);
                //it's a system app, not interested
            } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //Discard this one
                //in this case, it should be a user-installed app
            } else {
                installedApps.add(app);

                String label = (String)pm.getApplicationLabel(app);
                Drawable icon = pm.getApplicationIcon(app);

                Apps insApp = new Apps(label,icon);

                instdApp.add(insApp);

            }
        }




        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            Log.d("App Usage","Inside");

            UsageStatsManager usageStatsManager = (UsageStatsManager) getContext().getSystemService(getContext().USAGE_STATS_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            long start = calendar.getTimeInMillis();
            long end = System.currentTimeMillis();
            List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end);

            for(UsageStats us : stats){


            Log.d("App Usage", us.getPackageName()+"="+us.getLastTimeUsed());


            }

        }
        ins_apps = (RecyclerView) v.findViewById(R.id.ins_apps);

        mLayoutManager = new LinearLayoutManager(getContext());

        ins_apps.setLayoutManager(mLayoutManager);

        mAdapter = new MyAppAdapter(getContext(),instdApp);
        ins_apps.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        Log.d("Current Position", TabsActivity.tabIndex+"");

        return v;
    }


}

