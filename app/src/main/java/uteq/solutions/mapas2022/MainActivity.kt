package uteq.solutions.mapas2022

import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : FragmentActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    lateinit var mMap : GoogleMap
    var puntos: ArrayList<LatLng> = ArrayList<LatLng>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragmennt : SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragmennt.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        val camUpd1 : CameraUpdate =
            CameraUpdateFactory.newLatLngZoom(
                LatLng(-1.0799643034442739,
                    -79.50132897331231),
                20f)
        //mMap.moveCamera(camUpd1)

        val lineas = PolylineOptions()
            .add(LatLng(45.0, -12.0))
            .add(LatLng(45.0, 5.0))
            .add(LatLng(34.5, 5.0))
            .add(LatLng(34.5, -12.0))
            .add(LatLng(45.0, -12.0))

        lineas.width(8f)
        lineas.color(Color.RED)
        mMap.addPolyline(lineas)
        mMap.setOnMapClickListener(this)
        mMap.setOnMarkerClickListener(this)
    }

    override fun onMapClick(point: LatLng) {
        //  val proj : Projection= mMap.getProjection();
        // val coord : Point = proj.toScreenLocation(point);
        //  Toast.makeText(
        //  this.applicationContext,
        //   "Click\n" +
        //   "Lat: " + point.latitude + "\n" +
        //   "Lng: " + point.longitude + "\n" +
        //   "X: " + coord.x + " - Y: " + coord.y,
        //  Toast.LENGTH_SHORT).show();

        puntos.add(point)

        mMap.addMarker(
            MarkerOptions().position(point)
                .title("Lugar" + puntos.size));




        if(puntos.size == 4){
            var lineas: PolylineOptions = PolylineOptions()
            for(punto:LatLng in puntos)
            {
                lineas.add(punto)
            }
            lineas.add(puntos.get(0))
            lineas.width(8f)
            lineas.color(Color.RED)
            mMap.addPolyline((lineas))
            puntos.clear()
        }


    }

    override fun onMarkerClick(p0: Marker): Boolean {
        //Para borrar marcador
        p0.remove()
        var linea1= LatLng(p0.position.latitude, p0.position.longitude)
        //var linea2
        for(punto:LatLng in puntos)
        {
            if(linea1==punto)
            {
                puntos.remove(punto)
            }

        }
        return false
    }


}