package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.herts.partimer.R
import com.herts.partimer.request.Category
import com.herts.partimer.request.Roles
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.CategoryAdapter


class StudentProfile : AppCompatActivity(), CategoryAdapter.HomeListener {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, StudentProfile::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)
        setUpViews()
        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.setOnClickListener {
            Navigator.navigateToStudentSkills(this, updatedList)
        }
    }

    private fun setUpViews() {
        val retail = Category()
        retail.category = "Retail and Customer Service"
        val store = Roles()
        store.role = "Store helper"
        val cash = Roles()
        cash.role = "Cashier"
        val cust = Roles()
        cust.role = "Customer service representative"
        val retailRole = ArrayList<Roles>()
        retailRole.add(store)
        retailRole.add(cash)
        retailRole.add(cust)
        retail.role = retailRole


        val lang = Category()
        lang.category = "Language and Translation"
        val tutor = Roles()
        tutor.role = "Language tutor"
        val tran = Roles()
        tran.role = "Translator assistant"
        val langRole = ArrayList<Roles>()
        langRole.add(tutor)
        langRole.add(tran)
        lang.role = langRole


        val hos = Category()
        hos.category = "Hospitality and Food Service"
        val wait = Roles()
        wait.role = "Waitstaff"
        val bar = Roles()
        bar.role = "Bartender"
        val barista = Roles()
        barista.role = "Barista"
        val host = Roles()
        host.role = "Host/hostess"
        val hosRole = ArrayList<Roles>()
        hosRole.add(wait)
        hosRole.add(bar)
        hosRole.add(barista)
        hosRole.add(host)
        hos.role = hosRole


        val off = Category()
        off.category = "Office Support"
        val data = Roles()
        data.role = "Data entry"
        val rec = Roles()
        rec.role = "Receptionist"
        val offi = Roles()
        offi.role = "Office assistant"
        val offRole = ArrayList<Roles>()
        offRole.add(data)
        offRole.add(rec)
        offRole.add(offi)
        off.role = offRole


        val fit = Category()
        fit.category = "Fitness"
        val fitness = Roles()
        fitness.role = "Fitness assistant"
        val yoga = Roles()
        yoga.role = "Yoga instructor"
        val pers = Roles()
        pers.role = "Personal trainer"
        val fitRole = ArrayList<Roles>()
        fitRole.add(fitness)
        fitRole.add(yoga)
        fitRole.add(pers)
        fit.role = fitRole


        val eve = Category()
        eve.category = "Event Management"
        val help = Roles()
        help.role = "Helper"
        val setup = Roles()
        setup.role = "setup and teardown"
        val coord = Roles()
        coord.role = "coordinator assistant"
        val eveRole = ArrayList<Roles>()
        eveRole.add(help)
        eveRole.add(setup)
        eveRole.add(coord)
        eve.role = eveRole


        val pet = Category()
        pet.category = "Pet Care"
        val dog = Roles()
        dog.role = "Dog walker"
        val petT = Roles()
        petT.role = "Pet Trainer"
        val petG = Roles()
        petG.role = "Pet groomer assistant"
        val petRole = ArrayList<Roles>()
        petRole.add(dog)
        petRole.add(petT)
        petRole.add(petG)
        pet.role = petRole


        val house = Category()
        house.category = "Household Chores"
        val houseC = Roles()
        houseC.role = "House cleaner"
        val laundry = Roles()
        laundry.role = "Laundry helper"
        val org = Roles()
        org.role = "Organizing assistant"
        val houseRole = ArrayList<Roles>()
        houseRole.add(houseC)
        houseRole.add(laundry)
        houseRole.add(org)
        house.role = houseRole


        val cre = Category()
        cre.category = "Creative Services"
        val photo = Roles()
        photo.role = "Photography assistant"
        val video = Roles()
        video.role = "Videography assistant"
        val creRole = ArrayList<Roles>()
        creRole.add(photo)
        creRole.add(video)
        cre.role = creRole


        val gar = Category()
        gar.category = "Gardening"
        val garden = Roles()
        garden.role = "Gardening helper"
        val lawn = Roles()
        lawn.role = "Lawn care assistant"
        val garRole = ArrayList<Roles>()
        garRole.add(garden)
        garRole.add(lawn)
        gar.role = garRole


        val heal = Category()
        heal.category = "Healthcare Support"
        val medical = Roles()
        medical.role = "Medical office assistant"
        val healthC = Roles()
        healthC.role = "Healthcare facility support"
        val healRole = ArrayList<Roles>()
        healRole.add(medical)
        healRole.add(healthC)
        heal.role = healRole


        val baby = Category()
        baby.category = "Babysitting"
        val babySitter = Roles()
        babySitter.role = "Babysitter"
        val nanny = Roles()
        nanny.role = "Nanny"
        val after = Roles()
        after.role = "After-school caregiver"
        val babyRole = ArrayList<Roles>()
        babyRole.add(babySitter)
        babyRole.add(nanny)
        babyRole.add(after)
        baby.role = babyRole


        val categoryList = ArrayList<Category>()
        categoryList.add(retail)
        categoryList.add(lang)
        categoryList.add(hos)
        categoryList.add(off)
        categoryList.add(fit)
        categoryList.add(eve)
        categoryList.add(pet)
        categoryList.add(house)
        categoryList.add(cre)
        categoryList.add(gar)
        categoryList.add(heal)
        categoryList.add(baby)

        val categoryRv = findViewById<RecyclerView>(R.id.rv_cat)
        val layoutManager: RecyclerView.LayoutManager =
            StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL)
        categoryRv.layoutManager = layoutManager

        recyclerViewAdapter = CategoryAdapter(this, categoryList, this)
        categoryRv.adapter = recyclerViewAdapter
    }

    private lateinit var recyclerViewAdapter: CategoryAdapter
    var updatedList = ArrayList<Category>()

    override fun onItemClicked(postModel: Category?, position: Int) {
        if (!postModel?.isSelected!!) {
            postModel.isSelected = true
            updatedList.add(postModel)
        } else {
            postModel.isSelected = false
            updatedList.remove(postModel)
        }
        recyclerViewAdapter.notifyItemChanged(position)
    }
}