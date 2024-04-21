package com.example.localimage.ui.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localimage.R
import com.example.localimage.ui.adapters.ImageAdapter
import com.google.android.material.appbar.MaterialToolbar

class ImageGalleryFragment : Fragment() {
    private lateinit var toolbar: MaterialToolbar
    private val selectedImagesList = mutableListOf<Uri>()
    private lateinit var recyclerViewAdapter : ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_image_gallery, container, false)

        toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)

        // Set the toolbar as the activity's support action bar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receivedData = arguments?.getString("key")
        toolbar.title = receivedData

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(requireContext(), 4)
        recyclerView.layoutManager = gridLayoutManager

        recyclerViewAdapter = ImageAdapter(selectedImagesList)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_add -> {
                openGallery()
                true
            }
            else ->   super.onOptionsItemSelected(item)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val clipData = data.clipData
            if (clipData != null) {
                // Multiple images selected
                for (i in 0 until clipData.itemCount) {
                    val imageUri: Uri? = clipData.getItemAt(i).uri
                    imageUri?.let {
                        selectedImagesList.add(imageUri)
                    }
                }
            } else {
                val imageUri: Uri? = data.data
                imageUri?.let {
                    selectedImagesList.add(imageUri)
                }
            }
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }


    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 1
    }
}
