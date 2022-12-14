package com.serhatozturk.ilactedarik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.serhatozturk.ilactedarik.databinding.ActivityStokGirBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class StokGirActivity extends AppCompatActivity {
    private ActivityStokGirBinding binding;
    ActionBar actionBar;
    private String ilaclar[];
    private String stokSeviyesi[];
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


    String unvaniAld;
    String adresiAld;
    String telefonuAld;
    String mailiAld;
    String ilacSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStokGirBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        actionBar = getSupportActionBar();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        actionBar.setTitle("Stok Gir");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53 )));

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        binding.btnStokKaydet.setBackgroundColor(Color.rgb(203, 67, 53 ));

        ilaclar=new String[] {"ILAC ADI GIRINIZ","%0,9 ??ZOTON??K SODYUM KLOR??R, ",
                "%5 DEKSTROZ SOL??SYONU, ",
                "Abacavir",
                "Abacavir / dolutegravir / lamivudine (Triumeq??)",
                "Abacavir / lamivudine (Epzicom??)",
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
                "Atazanavir (Reyataz??)",
                "Atenolol",
                "Atovaquone",
                "Azithromycin",
                "Baclofen",
                "BEDEN DERECES??, ",
                "Bleomycin",
                "Bortezomib",
                "Bosentan",
                "BRAN??L, ",
                "Busulfan",
                "B??Y??K HAC??ML?? PARANTERAL SOL??SYONLAR, ",
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
                "Darunavir (Prezista??)",
                "Dasatinib",
                "Daunorubicin",
                "Deferasirox (Exjade??)",
                "DEKSTRAN 40/70, ",
                "Desmopressin (Stimate??)",
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
                "Efavirenz / emtricitabine / tenofovir (Atripla??)",
                "ELAST??K BANDAJ, ",
                "Eltrombopag",
                "Elvitegravir / cobicistat / emtricitabine / tenofovir (Stribild??)",
                "Elvitegravir / cobicistat / emtricitabine / tenofovir alafenamide (Genvoya??)",
                "Emicizumab",
                "Emtricitabine (Emtriva??)",
                "Emtricitabine / rilpivirine / tenofovir alafenamide (Odefsey??)",
                "Emtricitabine / tenofovir (Truvada??)",
                "Emtricitabine / tenofovir alafenamide (Descovy??)",
                "Enalapril",
                "Enoxaparin",
                "Erlotinib",
                "Erythromycin",
                "Erythropoietin",
                "Etonogestrel (Nexplanon??)",
                "Etoposide",
                "Etravirine (Intelence??)",
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
                "ISOSORBIT D??N??TRAT SUBL??NGUAL TAB., ",
                "Isotretinoin",
                "Itraconazole",
                "KALS??YUM ENJ., ",
                "KELEBEK SET, ",
                "Ketoconazole",
                "KLONOZEPAM TAB., ",
                "KLORPROMAZ??N ENJ., ",
                "KONTROLL?? SALIM SA??LAYAN MORF??N S??LFAT F??LM TAB., ",
                "KORT??KOSTERO??D ENJ., ",
                "Labetalol",
                "Lamivudine",
                "Leucovorin with high dose methotrexate (HDMTX)",
                "Levothyroxine",
                "L-glutamine",
                "Linezolid",
                "Lomustine",
                "Lopinavir / Ritonavir (Kaletra??)",
                "Lorazepam",
                "Lorlatinib",
                "Magnesium",
                "Maraviroc (Selzentry??)",
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
                "MORF??N S??LFAT TAB.,",
                "Morphine",
                "MULT??PL ELOKTROL??D SOL??SYONU, ",
                "Muromonab ??? CD3",
                "Mycophenolate mofetil",
                "Nelarabine",
                "Nelfinavir",
                "Neuromuscular blockers",
                "Nevirapine",
                "N??FED??P??N TAB., ",
                "N??TROGL??SER??N TAB./SPREY, ",
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
                "Raltegravir (Isentress??)",
                "Ranitidine",
                "Rasburicase",
                "Regorafenib",
                "Rilpivirine (Edurant??)",
                "Rilpivirine / emtricitabine / tenofovir (Complera??)",
                "Ritonavir",
                "Rituximab",
                "Rivaroxaban",
                "Ruxolitinib",
                "Sacubitril/valsartan (Entresto??)",
                "Saquinavir",
                "SARGI BEZ??, ",
                "SERUM SET??,",
                "Sirolimus",
                "SONDA (KADIN ERKEK EN AZ 3 DE????????K NUMARA)",
                "Sorafenib",
                "Stavudine",
                "STER??L ENJEKT??R (TEK KULLANIMLIK), ",
                "STER??L GAZ KOMPRES, ",
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
                "Tretinoin ??? applied to the skin",
                "Tretinoin ??? by mouth",
                "Trimethoprim / sulfamethoxazole",
                "TR??METOBENZAM??D ENJ., ",
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

        stokSeviyesi=new String[] {"Stok Seviyesi Giriniz", "Yok","Kritik","Orta","Yeterli"};

        ArrayAdapter<String> adapterIlac = new ArrayAdapter<String>(StokGirActivity.this, android.R.layout.simple_spinner_item, ilaclar);
        binding.spinnerIlac.setAdapter(adapterIlac);

        ArrayAdapter<String> adapterStokSeviyesi = new ArrayAdapter<String>(StokGirActivity.this, android.R.layout.simple_spinner_item, stokSeviyesi);
        binding.spinnerStokDurumu.setAdapter(adapterStokSeviyesi);

    }

    public boolean onSupportNavigateUp() {


        Intent intentToBosNorm = new Intent(StokGirActivity.this, TedarikciMainActivity.class);
        intentToBosNorm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToBosNorm);

        return super.onSupportNavigateUp();
    }

    public void stokGirClick(View view) {

        if (!binding.spinnerIlac.getSelectedItem().equals("ILAC ADI GIRINIZ") && !binding.spinnerStokDurumu.getSelectedItem().equals("Stok Seviyesi Giriniz")) {
            ilacSpinner = binding.spinnerIlac.getSelectedItem().toString();
            String stokSpinner = binding.spinnerStokDurumu.getSelectedItem().toString();

            Intent intent = getIntent();

            unvaniAld = intent.getStringExtra("unvaniAl");
            adresiAld = intent.getStringExtra("adresiAl");
            telefonuAld = intent.getStringExtra("telefonuAl");
            mailiAld = intent.getStringExtra("mailiAl");

            HashMap<String, Object> stokData = new HashMap<>(); // anahtar string, de??erler object olsun.
            stokData.put("usermail", mailiAld);
            stokData.put("ilacadi", ilacSpinner);
            stokData.put("stokdurumu", stokSpinner);
            stokData.put("unvanial", unvaniAld);
            stokData.put("adresiAld", adresiAld);
            stokData.put("telefonuAld", telefonuAld);
            stokData.put("date", FieldValue.serverTimestamp());



            firebaseFirestore.collection("ilacstoklar").add(stokData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(StokGirActivity.this, "Kay??t Ba??ar??l?? id: " + documentReference.getId(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(StokGirActivity.this, TedarikciStoklarimActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StokGirActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
        else {
            Toast.makeText(StokGirActivity.this, "Gerekli Alanlar?? Doldurunuz", Toast.LENGTH_LONG).show();
        }


    }









}