package com.example.mily_alpha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.example.mily_alpha.DB_Helper.IngredientDBHelper;
import com.example.mily_alpha.DB_Helper.UserDBHelper;
import com.example.mily_alpha.DB_Helper.User_IngreDBHelper;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    EditText mResultEt;
    ImageView mPreviewIv;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;
    private Button profileButton;
    private Button addProductButton;
    private Button fridgeButton;
    private Button editButton;

    public String NameUser;
    public String EmailUser;

    private Toast toast;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Click Image button  to insert Image");

        Intent startingIntent = getIntent();
        NameUser = startingIntent.getStringExtra("name");
        EmailUser = startingIntent.getStringExtra("email");

        Log.d("Namae User", "User name: " + NameUser);
        Log.d("Email User", "User email: " + EmailUser);


//        mResultEt = findViewById(R.id.resultEt);
        mPreviewIv = findViewById(R.id.ImageVw);

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        profileButton =  findViewById(R.id.profile_button);
        addProductButton = findViewById(R.id.addProducts_button);
        fridgeButton = findViewById(R.id.frig_button);
//        editButton = findViewById(R.id.edit_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(MainActivity.this, ProfileActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(MainActivity.this, MainActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendStuff = new Intent(MainActivity.this, ListCategoryActivity.class);
                sendStuff.putExtra("name", NameUser);
                sendStuff.putExtra("email",EmailUser);
                startActivity(sendStuff);
                finish();
            }
        });


        if(toast!=null)
            toast.cancel();
    }


    //actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //handle actionbar item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addImage){
            showImageImportDialog();
        }
        if(id == R.id.settings){
            Toast.makeText(this, "Setting",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        //items to display in dialog
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0)
                {
                    //camera option clicked
                    if(!checkCameraPermission ()){
                        // camera permission not allowed , request it
                        requestCameraPermission();
                    }else
                    {
                        // permission allowed, take picture
                        pickCamera();
                    }
                }
                if(which ==1)
                {
                    // gallery option clicked
                    if(!checkStoragePermission ()){
                        // storage permission not allowed , request it
                        requestStoragePermission();
                    }else
                    {
                        // permission allowed, take picture
                        pickGalery();
                    }
                }
            }
        });
        dialog.create().show();
    }

    private void pickGalery() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image To Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this , storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this , cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    //handle permission result


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    }
                    else{
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(writeStorageAccepted){
                        pickGalery();
                    }
                    else{
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
    }

    //handle image result


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //got image from gallery now crop
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON) //emable image guidlines
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //got image from camera now crop
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON) //emable image guidlines
                        .start(this);
            }
        }
        // get croped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri(); // get image uri
                //set image to image view

                mPreviewIv.setImageURI(resultUri);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                // Get text from image
                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    {
                    Python py =  Python.getInstance();
                    PyObject pyf = py.getModule("product_Categories");//name of the python file

                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    Dictionary geek = new Hashtable();
                    //get text from sb until  there is no text left
                    // Get just food products from image
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        PyObject obj = pyf.callAttr("test",myItem.getValue()); // definition name
                        if(!obj.equals("Detalii")) {
                            sb.append(obj.toString());
                        }
                    }
                    //set text to edit  text
                    String Categorie = "";
                    String[] list_product = sb.toString().split(",");
                    for(String product : list_product){
                        product =  product.replace("{","").replace("'","").replace("}","");
                        Categorie += product + "\n\n";
                        Log.w("Verify String: ", product);
                    }

                    setContentView(R.layout.activity_editproduct);
                    ActionBar actionBar = getSupportActionBar();
                    final EditText productTextView = findViewById(R.id.ProdusTextView);
                    final ListView productListView = findViewById(R.id.produsListView);
                    final ListView alteCategListView = findViewById(R.id.alteCategListView);
                    alteCategListView.setVisibility(View.GONE);
                    final Button alteCategButton = findViewById(R.id.alteCateg_button);
                    final Button nextProductButton =  findViewById(R.id.nextButton);
                    final Button addProductButton = findViewById(R.id.addButton);
                    final EditText dataExpirareText = findViewById(R.id.dataExpirareText);

                    final String[] productsAll = Categorie.split("\n");
                    final List<String> productsName = new ArrayList<String>();
                    final List<String> productsCateg = new ArrayList<String>();

                    // From python exec to : nume produse + categorii produse
                    for(String prodAll : productsAll){
                        String[] produ = prodAll.split(":");
                        if(produ[0] != " " && produ[0] != "\n" && produ[0]!= null && produ[0]!="") {
                            productsName.add(produ[0]);
                            if(produ.length > 1)
                                productsCateg.add(produ[1]);
                        }
                    }
                    Log.d("Products", productsName.toString());

                    // Clasa counter pentru afisarea pe rand produselor
                     class Counter{
                        int counter = 0;
                     }

                    // Clasa pentru salvarea detaliilor despre produse
                    class ProdusDetail{
                        String numeProdus = "";
                        String categorieProdus = "";
                        String dataExpirareProdus = "Nicio data adaugata!";
                    }

                    final Counter count = new Counter();
                    final ProdusDetail produsDetail = new ProdusDetail();

                    produsDetail.numeProdus = productsName.get(count.counter);
                    Log.d("Nume", produsDetail.numeProdus);
                    productTextView.setText(produsDetail.numeProdus);

                    // Afisarea listei generate de rowordnet a categoriilor sugerate
                    String[] listData = productsCateg.get(count.counter).split(";");
                    if(listData.length > 0 ) {
                        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
                        productListView.setAdapter(adapter);
                        count.counter++;
                    }

                    // Afisarea listei cu toate categoriile disponibile
                    String[] alteCategorii = {"legume", "fructe", "radacinoase", "lactate", "faina", "mirodenii"};
                    if(alteCategorii.length > 0 ) {
                        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alteCategorii);
                        alteCategListView.setAdapter(adapter);
                    }

                    // DB user -> ingredients
                    final UserDBHelper userDBHelper = new UserDBHelper(this);
                    final IngredientDBHelper ingredientDBHelper = new IngredientDBHelper(this);
                    final User_IngreDBHelper user_ingreDBHelper = new User_IngreDBHelper(this);

                    final int user_id = userDBHelper.getUserID(EmailUser, NameUser);

                    // Selectarea unei categorii din lista produsa
                    productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            view.setSelected(true);

                            produsDetail.categorieProdus = productListView.getItemAtPosition(i).toString();

                            Log.d("Categorie", "Categoria selectata !" + produsDetail.categorieProdus);
                            Toast.makeText(MainActivity.this, "Categoria selectata: " + produsDetail.categorieProdus, Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Selectarea unei categorii din lista totala
                    alteCategListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            view.setSelected(true);

                            produsDetail.categorieProdus = alteCategListView.getItemAtPosition(position).toString();

                            Log.d("Categorie", "Categoria selectata !" + produsDetail.categorieProdus);
                            Toast.makeText(MainActivity.this, "Categoria selectata: " + produsDetail.categorieProdus, Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Afisarea listei complete cu butonul alteCategButton
                    alteCategButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alteCategListView.setVisibility(View.VISIBLE);
                        }
                    });

                    // Adaugarea in baza de date a ingredientelor in legatura cu utilizatorul
                    addProductButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!produsDetail.numeProdus.equals("") && !produsDetail.categorieProdus.equals(""))
                            {
                                if(!dataExpirareText.getText().toString().equals("")){
                                    Log.d("Data Expirare", dataExpirareText.getText().toString());
                                    produsDetail.dataExpirareProdus = dataExpirareText.getText().toString();
                                }
                                if(ingredientDBHelper.addIngredient(produsDetail.numeProdus)){
                                    int ingredient_id = ingredientDBHelper.getIngredientID(produsDetail.numeProdus);
                                    if(user_ingreDBHelper.addUser_Ingredient(user_id, ingredient_id, produsDetail.dataExpirareProdus, produsDetail.categorieProdus))
                                        Log.d("User_Ingredient", "Add:" + produsDetail.categorieProdus);
                                    else
                                        Log.d("User_Ingredient", "Error 1!");
                                }
                                else {
                                    int ingredient_id = ingredientDBHelper.getIngredientID(produsDetail.numeProdus);
                                    if(user_ingreDBHelper.addUser_Ingredient(user_id, ingredient_id, produsDetail.dataExpirareProdus, produsDetail.categorieProdus))
                                        Log.d("User_Ingredient", "Add:" + produsDetail.categorieProdus);
                                    else
                                        Log.d("User_Ingredient", "Error 1!");
                                }

                                //Note gone just invisible and diseable pls
                                addProductButton.setEnabled(false);
                                addProductButton.setVisibility(View.INVISIBLE);
                            } else{
                                Toast.makeText(MainActivity.this, "Selecteaza o categorie!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //Afisarea urmatorului produs din lista cu categoriile acestuia
                    nextProductButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                            public void onClick(View v) {
                                addProductButton.setEnabled(true);
                                addProductButton.setVisibility(View.VISIBLE);
                                dataExpirareText.setText("");

                                if(productsName.size() > count.counter)
                                    produsDetail.numeProdus = productsName.get(count.counter);

                                if(count.counter < productsName.size()){
                                    productTextView.setText(productsName.get(count.counter));

                                    String[] listData = productsCateg.get(count.counter).split(";");
                                    if(listData.length > 0 ) {
                                        ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listData);
                                        productListView.setAdapter(adapter);
                                    }

                                    count.counter++;

                                    Log.d("Neeeeext", "Lista este afisata !");
                                }
                                else if(count.counter == productsName.size()){
                                    Intent sendStuff = new Intent(MainActivity.this, ListCategoryActivity.class);
                                    sendStuff.putExtra("name", NameUser);
                                    sendStuff.putExtra("email",EmailUser);
                                    startActivity(sendStuff);
                                    finish();
                                }
                            }
                    });
                }
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //if there is any error show it
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}