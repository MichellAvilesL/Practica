package uteq.solutions.mapas2022

import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener {
    lateinit var mMap : GoogleMap
    var puntos : ArrayList<LatLng> = ArrayList<LatLng>()
    var marcadores : ArrayList<Marker> = ArrayList<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment: SupportMapFragment = getSupportFragmentManager()
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this);

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        val camUpd1: CameraUpdate =
            CameraUpdateFactory.newLatLngZoom(
                LatLng(-1.0799, -79.50133), 25F);

        mMap.moveCamera(camUpd1);

        mMap.setOnMapClickListener(this)
    }

    override fun onMapClick(point: LatLng) {
        val proj : Projection = mMap.getProjection();
        val coord : Point = proj.toScreenLocation(point);

        Toast.makeText(
            this.applicationContext,
            "Click\n" +
                    "Lat: " + point.latitude + "\n" +
                    "Lng: " + point.longitude + "\n" +
                    "X: " + coord.x + " - Y: " + coord.y,
            Toast.LENGTH_SHORT).show();

        puntos.add(point)
        mMap.addMarker(MarkerOptions().position(point)
            .title("Punto " + puntos.size))

        mMap.setOnMarkerClickListener(this)

        if(puntos.size == 4){
            var lineas : PolylineOptions = PolylineOptions()

            for (punto: LatLng in puntos){

                lineas.add(punto)
            }

            lineas.add(puntos.get(0))
            lineas.width(8F)
            lineas.color(Color.RED)
            mMap.addPolyline(lineas)

            puntos.clear()

        }

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.remove()
        return false
    }
}