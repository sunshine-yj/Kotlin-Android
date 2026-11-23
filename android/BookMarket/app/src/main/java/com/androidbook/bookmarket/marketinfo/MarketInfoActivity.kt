package com.androidbook.bookmarket.marketinfo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidbook.bookmarket.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MarketInfoActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketinfo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.marketinfo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        val mapFragment = supportFragmentManager .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(37.5591022,126.9278237)

        googleMap.addMarker(
            MarkerOptions().position(latLng).title("한빛아카데미")
            .snippet("서울 서대문구 연희로2길 62")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.hanbit_location))
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.getUiSettings()?.setZoomControlsEnabled(true)

        googleMap.setInfoWindowAdapter(object : InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val v: View = layoutInflater.inflate(R.layout.marketinfo_marker, null)

                val note = v.findViewById<View>(R.id.info) as TextView
                note.text = """
                            ${marker.title}
                            위도: ${marker.position.latitude}
                            경도: ${marker.position.longitude}
                            """.trimIndent()
                return v
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}