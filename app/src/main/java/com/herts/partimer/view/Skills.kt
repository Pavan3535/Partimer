package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.herts.partimer.R
import com.herts.partimer.request.Cat
import com.herts.partimer.request.Category
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.SkillAdapter
import java.util.StringJoiner


class Skills : AppCompatActivity() {

    var categoryList: ArrayList<Category>? = null

    companion object {
        var CATEGORY: String = "CATEGORY"
        fun getCallingIntent(context: Context, updatedList: ArrayList<Category>): Intent {
            val intent = Intent(context, Skills::class.java)
            intent.putExtra(CATEGORY, updatedList)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skills)
        val bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                categoryList =
                    intent.getSerializableExtra(CATEGORY, ArrayList<Category>()::class.java)
            } else {
                categoryList = intent.getSerializableExtra(CATEGORY) as ArrayList<Category>?
            }
        }
        updateViews()
        val btn_save_s = findViewById<Button>(R.id.btn_save_s)
        val catList = ArrayList<Cat>()
        btn_save_s.setOnClickListener {
            catList.clear()
            categoryList?.forEach {
                val cat = Cat()
                cat.category = it.category
                val joinNames = StringJoiner(",")
                it.role?.forEach {
                    if (it.isSelected) {
                        joinNames.add(it.role)
                    }
                }
                cat.role = joinNames.toString()
                catList.add(cat)
            }


            Navigator.navigateToStudentMoreDetails(this, catList)
        }

    }

    private lateinit var recyclerViewAdapter: SkillAdapter

    private fun updateViews() {
        val retail = findViewById<TextView>(R.id.retail)
        val retailLl = findViewById<LinearLayout>(R.id.retail_ll)

        val office = findViewById<TextView>(R.id.office)
        val officeLl = findViewById<LinearLayout>(R.id.officeLl)

        val fitness = findViewById<TextView>(R.id.fitness)
        val fitnessLl = findViewById<LinearLayout>(R.id.fitnessLl)

        val event = findViewById<TextView>(R.id.event)
        val eventLl = findViewById<LinearLayout>(R.id.eventLl)

        val pet = findViewById<TextView>(R.id.pet)
        val petLl = findViewById<LinearLayout>(R.id.petLl)

        val house = findViewById<TextView>(R.id.house)
        val houseLl = findViewById<LinearLayout>(R.id.houseLl)

        val creative = findViewById<TextView>(R.id.creative)
        val creativeLl = findViewById<LinearLayout>(R.id.creativeLl)

        val garden = findViewById<TextView>(R.id.garden)
        val gardenLl = findViewById<LinearLayout>(R.id.gardenLl)

        val health = findViewById<TextView>(R.id.health)
        val healthLl = findViewById<LinearLayout>(R.id.healthLl)

        val lang = findViewById<TextView>(R.id.lang)
        val langLl = findViewById<LinearLayout>(R.id.langLl)

        val hos = findViewById<TextView>(R.id.hos)
        val hosLl = findViewById<LinearLayout>(R.id.hosLl)

        val baby = findViewById<TextView>(R.id.baby)
        val babyLl = findViewById<LinearLayout>(R.id.babyLl)


        categoryList?.forEach {
            when (it.category) {
                "Retail and Customer Service" -> {
                    retail.visibility = View.VISIBLE
                    retailLl.visibility = View.VISIBLE
                }

                "Office Support" -> {
                    office.visibility = View.VISIBLE
                    officeLl.visibility = View.VISIBLE
                }

                "Fitness" -> {
                    fitness.visibility = View.VISIBLE
                    fitnessLl.visibility = View.VISIBLE
                }

                "Event Management" -> {
                    event.visibility = View.VISIBLE
                    eventLl.visibility = View.VISIBLE
                }

                "Pet Care" -> {
                    pet.visibility = View.VISIBLE
                    petLl.visibility = View.VISIBLE
                }

                "Household Chores" -> {
                    house.visibility = View.VISIBLE
                    houseLl.visibility = View.VISIBLE
                }

                "Creative Services" -> {
                    creative.visibility = View.VISIBLE
                    creativeLl.visibility = View.VISIBLE
                }

                "Gardening" -> {
                    garden.visibility = View.VISIBLE
                    gardenLl.visibility = View.VISIBLE
                }

                "Healthcare Support" -> {
                    health.visibility = View.VISIBLE
                    healthLl.visibility = View.VISIBLE
                }

                "Language and Translation" -> {
                    lang.visibility = View.VISIBLE
                    langLl.visibility = View.VISIBLE
                }

                "Hospitality and Food Service" -> {
                    hos.visibility = View.VISIBLE
                    hosLl.visibility = View.VISIBLE
                }

                "Babysitting" -> {
                    baby.visibility = View.VISIBLE
                    babyLl.visibility = View.VISIBLE
                }
            }
        }

        val store = findViewById<TextView>(R.id.store)
        store.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Store helper") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            store.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            store.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val cashier = findViewById<TextView>(R.id.cashier)
        cashier.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Cashier") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            cashier.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            cashier.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }

        val customer = findViewById<TextView>(R.id.customer)
        customer.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Customer service representative") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            customer.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            customer.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }

        val data = findViewById<TextView>(R.id.data)
        data.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Data entry") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            data.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            data.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }

        val receptionist = findViewById<TextView>(R.id.receptionist)
        receptionist.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Receptionist") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            receptionist.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            receptionist.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }

        val off = findViewById<TextView>(R.id.off)
        off.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Office assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            off.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            off.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }

        val fit = findViewById<TextView>(R.id.fit)
        fit.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Fitness assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            fit.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            fit.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val yoga = findViewById<TextView>(R.id.yoga)
        yoga.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Yoga instructor") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            yoga.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            yoga.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }

        val personal = findViewById<TextView>(R.id.personal)
        personal.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Personal trainer") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            personal.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            personal.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }

        val help = findViewById<TextView>(R.id.help)
        help.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Helper") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            help.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            help.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val setup = findViewById<TextView>(R.id.setup)
        setup.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "setup and teardown") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            setup.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            setup.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val coord = findViewById<TextView>(R.id.coord)
        coord.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "coordinator assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            coord.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            coord.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val dog = findViewById<TextView>(R.id.dog)
        dog.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Dog walker") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            dog.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            dog.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val petT = findViewById<TextView>(R.id.petT)
        petT.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Pet Trainer") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            petT.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            petT.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val petG = findViewById<TextView>(R.id.petG)
        petG.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Pet groomer assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            petG.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            petG.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val houseC = findViewById<TextView>(R.id.houseC)
        houseC.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "House cleaner") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            houseC.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            houseC.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val laundry = findViewById<TextView>(R.id.laundry)
        laundry.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Laundry helper") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            laundry.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            laundry.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val organ = findViewById<TextView>(R.id.organ)
        organ.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Organizing assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            organ.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            organ.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val photo = findViewById<TextView>(R.id.photo)
        photo.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Photography assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            photo.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            photo.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val video = findViewById<TextView>(R.id.video)
        video.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Videography assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            video.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            video.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val gardenH = findViewById<TextView>(R.id.gardenH)
        gardenH.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Gardening helper") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            gardenH.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            gardenH.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val lawn = findViewById<TextView>(R.id.lawn)
        lawn.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Lawn care assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            lawn.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            lawn.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val medical = findViewById<TextView>(R.id.medical)
        medical.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Medical office assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            medical.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            medical.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val facility = findViewById<TextView>(R.id.facility)
        facility.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Healthcare facility support") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            facility.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            facility.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val tutor = findViewById<TextView>(R.id.tutor)
        tutor.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Language tutor") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            tutor.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            tutor.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val trans = findViewById<TextView>(R.id.trans)
        trans.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Translator assistant") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            trans.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            trans.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val wait = findViewById<TextView>(R.id.wait)
        wait.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Waitstaff") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            wait.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            wait.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val bar = findViewById<TextView>(R.id.bar)
        bar.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Bartender") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            bar.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            bar.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val barista = findViewById<TextView>(R.id.barista)
        barista.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Barista") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            barista.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            barista.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val host = findViewById<TextView>(R.id.host)
        host.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Host/hostess") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            host.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            host.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val babyS = findViewById<TextView>(R.id.babyS)
        babyS.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Babysitter") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            babyS.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            babyS.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val nanny = findViewById<TextView>(R.id.nanny)
        nanny.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "Nanny") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            nanny.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            nanny.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
        val after = findViewById<TextView>(R.id.after)
        after.setOnClickListener {
            categoryList?.forEach { it ->
                it.role?.forEach {
                    if (it.role == "After-school caregiver") {
                        if (!it.isSelected) {
                            it.isSelected = true
                            after.setBackgroundResource(R.drawable.textview_clicked)
                        } else {
                            it.isSelected = false
                            after.setBackgroundResource(R.drawable.textview)
                        }
                    }
                }
            }
        }
    }


}