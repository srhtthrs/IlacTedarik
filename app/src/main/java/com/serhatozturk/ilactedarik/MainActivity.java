package com.serhatozturk.ilactedarik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.serhatozturk.ilactedarik.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBar actionBar;
    private String ilaclar[];
    private FirebaseFirestore firebaseFirestore;
    ArrayList<ModelTedStok> modelTedStokArrayList;
    AdapterTumStok adapterTumStok;
    //private FirebaseAuth mAuth;
    //String user;
    String ilacAdiAlSpinner, telGonder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        actionBar = getSupportActionBar();
        actionBar.setTitle("İlaç Tedarik");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53)));
        binding.btnAra.setBackgroundColor(Color.rgb(203, 67, 53));

        modelTedStokArrayList = new ArrayList<>();

        //   mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapterTumStok = new AdapterTumStok(modelTedStokArrayList);

        binding.recyclerView2.setAdapter(adapterTumStok);



        ilaclar = new String[]{"ILAC ADI GIRINIZ", "%0,9 İZOTONİK SODYUM KLORÜR, ",
                "%5 DEKSTROZ SOLÜSYONU, ",
                "Abacavir",
                "Abacavir / dolutegravir / lamivudine (Triumeq®)",
                "Abacavir / lamivudine (Epzicom®)",
                "Acyclovir",
                "Alemtuzumab",
                "Alendronate",
                "Allopurinol",
                "Amifostine",
                "Amikacin",
                "Aminocaproic Acid",
                "Amitriptyline",
                "Amlodipine",
                "Amoxicillin",
                "Amoxicillin / clavulanic acid",
                "Amphotericin B",
                "Ampicillin",
                "Anti-Inhibitor Coagulant Complex (FEIBA)",
                "Anti-thymocyte globulin",
                "Aprepitant",
                "Asparaginase",
                "Atazanavir (Reyataz®)",
                "Atenolol",
                "Atovaquone",
                "Azithromycin",
                "Baclofen",
                "BEDEN DERECESİ, ",
                "Bleomycin",
                "Bortezomib",
                "Bosentan",
                "BRANÜL, ",
                "Busulfan",
                "BÜYÜK HACİMLİ PARANTERAL SOLÜSYONLAR, ",
                "Calcium",
                "Captopril",
                "Carbamazepine",
                "Carboplatin",
                "Carmustine",
                "Cefaclor",
                "Cefepime",
                "Cefixime",
                "Ceftazidime",
                "Cefuroxime",
                "Celecoxib",
                "Cephalexin",
                "Cidofovir",
                "Cisplatin",
                "Cladribine",
                "Clarithromycin",
                "Clindamycin",
                "Clobazam",
                "Clofarabine",
                "Codeine",
                "Crizanlizumab",
                "Crizotinib",
                "Cyclobenzaprine",
                "Cyclophosphamide",
                "Cyclosporine",
                "Cyproheptadine",
                "Cytarabine",
                "Dacarbazine",
                "Dactinomycin",
                "Dapsone",
                "Darunavir (Prezista®)",
                "Dasatinib",
                "Daunorubicin",
                "Deferasirox (Exjade®)",
                "DEKSTRAN 40/70, ",
                "Desmopressin (Stimate®)",
                "Dexamethasone",
                "DIAZEPAM TAB., ",
                "Diclofenac",
                "Didanosine",
                "DIGOKSIN ENJ., ",
                "Dinutuximab",
                "Dobutamine",
                "Dopamine",
                "Dornase alfa",
                "Doxorubicin",
                "Dronabinol",
                "Efavirenz",
                "Efavirenz / emtricitabine / tenofovir (Atripla®)",
                "ELASTİK BANDAJ, ",
                "Eltrombopag",
                "Elvitegravir / cobicistat / emtricitabine / tenofovir (Stribild®)",
                "Elvitegravir / cobicistat / emtricitabine / tenofovir alafenamide (Genvoya®)",
                "Emicizumab",
                "Emtricitabine (Emtriva®)",
                "Emtricitabine / rilpivirine / tenofovir alafenamide (Odefsey®)",
                "Emtricitabine / tenofovir (Truvada®)",
                "Emtricitabine / tenofovir alafenamide (Descovy®)",
                "Enalapril",
                "Enoxaparin",
                "Erlotinib",
                "Erythromycin",
                "Erythropoietin",
                "Etonogestrel (Nexplanon®)",
                "Etoposide",
                "Etravirine (Intelence®)",
                "Factor IX complex",
                "Factor IX concentrate",
                "Factor VIIa (Recombinant)",
                "Factor VIII (Human) and von Willebrand Factor",
                "Factor VIII (Recombinant)",
                "Famciclovir",
                "Famotidine",
                "Fidaxomicin",
                "FLASTER, ",
                "Fluconazole",
                "Fludarabine",
                "Fluorouracil",
                "Foscarnet",
                "Furosemide",
                "Gabapentin",
                "Ganciclovir",
                "G-CSF (Filgrastim)",
                "Gefitinib",
                "Gemcitabine",
                "Gemtuzumab ozogamicin",
                "GM-CSF (Sargramostim)",
                "Granisetron",
                "Heparin Lock Flush for children and young adults",
                "Heparin Lock Flush for infants",
                "Hydralazine",
                "Hydrocodone with acetaminophen",
                "Hydrocortisone",
                "Hydromorphone",
                "Hydroxyurea",
                "Hydroxyurea for sickle cell disease",
                "Ifosfamide",
                "ILAC STOK BILGISI GIRINIZ,",
                "Imatinib",
                "Imipenem / cilastatin",
                "Immune globulin",
                "INSULIN KRISTALIZE ENJ., ",
                "INSULIN NPH, ",
                "Interferon alfa-2a and alfa-2b",
                "Interferon alfa-2b for melanoma",
                "Interleukin-2 (Aldesleukin)",
                "Irinotecan",
                "ISOSORBIT DİNİTRAT SUBLİNGUAL TAB., ",
                "Isotretinoin",
                "Itraconazole",
                "KALSİYUM ENJ., ",
                "KELEBEK SET, ",
                "Ketoconazole",
                "KLONOZEPAM TAB., ",
                "KLORPROMAZİN ENJ., ",
                "KONTROLLÜ SALIM SAĞLAYAN MORFİN SÜLFAT FİLM TAB., ",
                "KORTİKOSTEROİD ENJ., ",
                "Labetalol",
                "Lamivudine",
                "Leucovorin with high dose methotrexate (HDMTX)",
                "Levothyroxine",
                "L-glutamine",
                "Linezolid",
                "Lomustine",
                "Lopinavir / Ritonavir (Kaletra®)",
                "Lorazepam",
                "Lorlatinib",
                "Magnesium",
                "Maraviroc (Selzentry®)",
                "Mechlorethamine",
                "Megestrol acetate",
                "Meloxicam",
                "Melphalan",
                "Meperidine",
                "Mercaptopurine",
                "Meropenem",
                "Mesna",
                "Methadone",
                "Methotrexate",
                "Methylphenidate",
                "Metronidazole",
                "Micafungin",
                "Mitotane",
                "Mitoxantrone",
                "Modafinil",
                "MORFİN SÜLFAT TAB.,",
                "Morphine",
                "MULTİPL ELOKTROLİD SOLÜSYONU, ",
                "Muromonab – CD3",
                "Mycophenolate mofetil",
                "Nelarabine",
                "Nelfinavir",
                "Neuromuscular blockers",
                "Nevirapine",
                "NİFEDİPİN TAB., ",
                "NİTROGLİSERİN TAB./SPREY, ",
                "Norepinephrine",
                "Omeprazole",
                "Ondansetron",
                "Oxycodone",
                "Paclitaxel",
                "PEGaspargase",
                "Pegfilgrastim",
                "Pemetrexed",
                "Penicillin VK",
                "Pentamidine (inhaled by mouth)",
                "Phenobarbital",
                "Phenytoin",
                "Phosphorus",
                "Posaconazole",
                "Potassium",
                "Prednisone",
                "Probenecid",
                "Procarbazine",
                "Promethazine",
                "Promethazine topical gel",
                "Propoxyphene",
                "Raltegravir (Isentress®)",
                "Ranitidine",
                "Rasburicase",
                "Regorafenib",
                "Rilpivirine (Edurant®)",
                "Rilpivirine / emtricitabine / tenofovir (Complera®)",
                "Ritonavir",
                "Rituximab",
                "Rivaroxaban",
                "Ruxolitinib",
                "Sacubitril/valsartan (Entresto®)",
                "Saquinavir",
                "SARGI BEZİ, ",
                "SERUM SETİ,",
                "Sirolimus",
                "SONDA (KADIN ERKEK EN AZ 3 DEĞİŞİK NUMARA)",
                "Sorafenib",
                "Stavudine",
                "STERİL ENJEKTÖR (TEK KULLANIMLIK), ",
                "STERİL GAZ KOMPRES, ",
                "Sucralfate",
                "Sugammadex",
                "Sunitinib",
                "Tacrolimus",
                "Temozolomide",
                "Teniposide",
                "Tenofovir",
                "Thioguanine",
                "Thiotepa",
                "Tobramycin",
                "Topotecan",
                "Tretinoin – applied to the skin",
                "Tretinoin – by mouth",
                "Trimethoprim / sulfamethoxazole",
                "TRİMETOBENZAMİD ENJ., ",
                "Valproic acid",
                "Vancomycin",
                "Vinblastine",
                "Vincristine",
                "Voriconazole",
                "Vorinostat",
                "Voxelotor",
                "Warfarin",
                "Zidovudine"


        };


        ArrayAdapter<String> adapterIlac = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, ilaclar);
        binding.spinnerIlac2.setAdapter(adapterIlac);


        // getDataBosStok();


        Intent intent = getIntent();
        telGonder = intent.getStringExtra("telGonder");
        if (telGonder != null) {
            Intent intentTel = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telGonder, null));
            startActivity(intentTel);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anamenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.hakkinda) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Hakkinda: ");
            alert.setMessage("Yakin Cevrenizdeki Ecza Depolarinin Ilac Stoklarina Ulasip, Ecza Depolari Ile Iletisime Gecebilmeniz Amaciyla Hazirlanmistir.");
            alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();


        }

        return super.onOptionsItemSelected(item);


    }



    public void araClick (View view){

        binding.recyclerView2.setVisibility(View.VISIBLE);
        ilacAdiAlSpinner = binding.spinnerIlac2.getSelectedItem().toString();


        if (binding.spinnerIlac2.getSelectedItem().equals("ILAC ADI GIRINIZ")) {

            Toast.makeText(MainActivity.this, "Tum stoklar listelenecektir.", Toast.LENGTH_LONG).show();

            firebaseFirestore.collection("ilacstoklar").orderBy("ilacadi", Query.Direction.ASCENDING).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                    if (value != null) {
                        modelTedStokArrayList.clear();



                            for (DocumentSnapshot snapshot : value.getDocuments()) {

                                Map<String, Object> data = snapshot.getData();
                                String ilac = (String) data.get("ilacadi");
                                String stok = (String) data.get("stokdurumu");
                                String adres = (String) data.get("adresiAld");
                                String telefon = (String) data.get("telefonuAld");
                                String unvan = (String) data.get("unvanial");
                                String id= snapshot.getId();
                                ModelTedStok pst = new ModelTedStok("", "    Adres: " + adres, "", "    " + ilac, "    Stok Durumu: " + stok, "    Telefon: " + telefon, "    Unvan: " + unvan, "");
                                modelTedStokArrayList.add(pst);
                            }





                                }




                        adapterTumStok.notifyDataSetChanged();  // değişikleri göstermek.


                }

            });


        } else {

            firebaseFirestore.collection("ilacstoklar").whereEqualTo("ilacadi", "" + ilacAdiAlSpinner).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                    if (value != null) {

                        modelTedStokArrayList.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Map<String, Object> data = snapshot.getData();




                                String ilac = (String) data.get("ilacadi");
                                String stok = (String) data.get("stokdurumu");
                                String adres = (String) data.get("adresiAld");
                                String telefon = (String) data.get("telefonuAld");
                                String unvan = (String) data.get("unvanial");
                                //   String id= snapshot.getId();
                                ModelTedStok pst = new ModelTedStok("", "    Adres: " + adres, "", "    " + ilac, "    Stok Durumu: " + stok, "    Telefon: " + telefon, "    Unvan: " + unvan, "");



                                   modelTedStokArrayList.add(pst);
                                   Toast.makeText(MainActivity.this, " " +modelTedStokArrayList.size()+  " adet kayit bulundu", Toast.LENGTH_LONG).show();







                            }

                        if(modelTedStokArrayList.isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "Aradiginiz ilac tedarikcilerin stoklarinda bulunmamaktadir.", Toast.LENGTH_LONG).show();
                        }


                        adapterTumStok.notifyDataSetChanged();  // değişikleri göstermek.
                    }

                }
            });


        }



    }


}