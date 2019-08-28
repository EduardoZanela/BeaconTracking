package com.eduardozanela.beacondetector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ListView;

import com.eduardozanela.beacondetector.adapter.BeaconListAdapter;
import com.eduardozanela.beacondetector.model.Distance;
import com.eduardozanela.beacondetector.model.Position;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    private ListView listView;

    private BeaconManager altBeaconBeaconManager;
    private BackgroundPowerSaver backgroundPowerSaver;

    private static final Set<Beacon> listBeacons = new LinkedHashSet<>();

    private static final String TAG = "EDDYSTONE_BEACON";
    private static final String IBEACON = "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24";
    private static final String ESTIMOTE_DEFAULT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        listView = findViewById(R.id.beacons_list);

        // USING ALTBEACON SDK
        altBeaconBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());

        // Detect the main identifier (UID) frame:
        altBeaconBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON));
        altBeaconBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(ESTIMOTE_DEFAULT));
        altBeaconBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        // Bind this class to ranging
        altBeaconBeaconManager.bind(this);

        // Period between scans it will wait 5 seconds to scan again
        altBeaconBeaconManager.setForegroundBetweenScanPeriod(5000);
        // Period between background scans it will wait 1 minute to scan again
        altBeaconBeaconManager.setBackgroundBetweenScanPeriod(60000);

        // Simply constructing this class and holding a reference to it in your custom Application class
        // enables auto battery saving of about 60%
        backgroundPowerSaver = new BackgroundPowerSaver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        // not needed to scan in background
        //altBeaconBeaconManager.bind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // not needed to scan in background
        //altBeaconBeaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region("my-beacon-region", null, null, null);

        try {
            altBeaconBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // Add ranging notifier
        altBeaconBeaconManager.addRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> beacons, Region region) {

        if(!beacons.isEmpty()){
            altBeaconBeaconManager.setForegroundBetweenScanPeriod(2000);
        } else {
            altBeaconBeaconManager.setForegroundBetweenScanPeriod(30000);
        }

        Position position = new Position();

        for (org.altbeacon.beacon.Beacon beacon: beacons) {
            // if is a eddystone protocol
            if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x00) {

                // Add beacon to the list to show later
                listBeacons.add(beacon);

                // This is a Eddystone-UID frame
                Identifier namespaceId = beacon.getId1();
                Identifier instanceId = beacon.getId2();

                Log.d(TAG, "I see a beacon transmitting namespace id: " + namespaceId +
                        " and instance id: " + instanceId +
                        " approximately " + beacon.getDistance() + " meters away.");

                sendBeaconToApi(beacon, position);
            }
        }
        // Put list on layout
        inflateListView(new ArrayList<>(beacons));
    }

    private void sendBeaconToApi(Beacon beacon, Position position) {
        Distance distance = new Distance();
        String namespace = beacon.getId1().toString();
        String instanceId = beacon.getId2().toString();
        distance.setUuid(namespace.concat(";").concat(instanceId));
        distance.setDistance(beacon.getDistance());
        position.addDistance(distance);
    }

    private void inflateListView(List<Beacon> list) {
        BeaconListAdapter adapter = new BeaconListAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setDivider(null);
        listView.setDividerHeight(0);
    }

}
