package com.palabradedios.biblia

import android.app.Activity
import android.os.Bundle
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.Context
import java.util.Calendar

class MainActivity : Activity() {
 data class Verse(val ref:String,val text:String)
 private val verses = listOf(
  Verse("Juan 3:16","Porque de tal manera amó Dios al mundo, que ha dado á su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna."),
  Verse("Salmos 23:1","JEHOVÁ es mi pastor; nada me faltará."),
  Verse("Proverbios 3:5","Fíate de Jehová de todo tu corazón; y no estribes en tu prudencia."),
  Verse("Mateo 6:33","Mas buscad primeramente el reino de Dios y su justicia, y todas estas cosas os serán añadidas."),
  Verse("Filipenses 4:13","Todo lo puedo en Cristo que me fortalece."),
  Verse("Isaías 41:10","No temas, que yo soy contigo; no desmayes, que yo soy tu Dios que te esfuerzo."),
  Verse("Romanos 8:28","Y sabemos que á los que á Dios aman, todas las cosas les ayudan á bien."),
  Verse("Salmos 46:1","Dios es nuestro amparo y fortaleza, nuestro pronto auxilio en las tribulaciones."),
  Verse("Mateo 11:28","Venid á mí todos los que estáis trabajados y cargados, que yo os haré descansar."),
  Verse("Josué 1:9","Mira que te mando que te esfuerces y seas valiente; no temas ni desmayes."),
  Verse("1 Corintios 13:4","La caridad es sufrida, es benigna; la caridad no tiene envidia."),
  Verse("Salmos 119:105","Lámpara es á mis pies tu palabra, y lumbrera á mi camino."),
  Verse("Juan 14:6","Jesús le dice: Yo soy el camino, y la verdad, y la vida."),
  Verse("1 Pedro 5:7","Echando toda vuestra solicitud en él, porque él tiene cuidado de vosotros."),
  Verse("Miqueas 6:8","Oh hombre, él te ha declarado qué sea lo bueno, y qué pida de ti Jehová."),
  Verse("Gálatas 6:9","No nos cansemos, pues, de hacer bien; que á su tiempo segaremos."),
  Verse("Salmos 34:8","Gustad, y ved que es bueno Jehová: dichoso el hombre que confiará en él."),
  Verse("Romanos 12:12","Gozosos en la esperanza; sufridos en la tribulación; constantes en la oración."),
  Verse("Efesios 6:10","Confortaos en el Señor, y en la potencia de su fortaleza."),
  Verse("Santiago 4:8","Allegaos á Dios, y él se allegará á vosotros."),
  Verse("Mateo 5:9","Bienaventurados los pacificadores: porque ellos serán llamados hijos de Dios."),
  Verse("Salmos 27:1","Jehová es mi luz y mi salvación: ¿de quién temeré?"),
  Verse("1 Juan 4:19","Nosotros le amamos á él, porque él nos amó primero."),
  Verse("Proverbios 16:3","Encomienda á Jehová tus obras, y tus pensamientos serán afirmados."),
  Verse("Juan 15:12","Que os améis los unos á los otros, como yo os he amado."),
  Verse("2 Timoteo 1:7","Dios no nos ha dado espíritu de temor, sino de fortaleza, de amor y de templanza."),
  Verse("Salmos 37:5","Encomienda á Jehová tu camino, y espera en él; y él hará."),
  Verse("Colosenses 3:23","Todo lo que hagáis, hacedlo de ánimo, como al Señor."),
  Verse("Hebreos 11:1","La fe es la sustancia de las cosas que se esperan, la demostración de las cosas que no se ven."),
  Verse("Números 6:24","Jehová te bendiga, y te guarde.")
 )
 private val prefs by lazy { getSharedPreferences("biblia",Context.MODE_PRIVATE) }
 private val paper=0xFFF7F3EA.toInt()
 private val ink=0xFF403426.toInt()

 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);showHome()}
 private fun label(s:String,n:Float,b:Boolean=false)=TextView(this).apply{
  text=s;textSize=n;setTextColor(ink);if(b)typeface=Typeface.DEFAULT_BOLD;setPadding(0,10,0,10)
 }
 private fun btn(s:String,f:()->Unit)=Button(this).apply{text=s;setOnClickListener{f()}}
 private fun showHome(){
  val scroll=ScrollView(this)
  val col=LinearLayout(this).apply{orientation=1;setPadding(26,36,26,28);setBackgroundColor(paper)}
  col.addView(TextView(this).apply{text="BIBLIA";textSize=32f;gravity=Gravity.CENTER;setTextColor(0xFF4A3826.toInt());typeface=Typeface.DEFAULT_BOLD})
  col.addView(TextView(this).apply{text="PALABRA DE DIOS";textSize=12f;gravity=Gravity.CENTER;letterSpacing=.22f;setTextColor(0xFF92764D.toInt())})
  col.addView(label("Versículo del día",22f,true).apply{setPadding(0,34,0,12)})
  val v=verses[(Calendar.getInstance().get(Calendar.DAY_OF_YEAR)-1)%verses.size]
  val card=LinearLayout(this).apply{orientation=1;setPadding(20,22,20,22);setBackgroundColor(0xFFECE3D2.toInt())}
  card.addView(label("“"+v.text+"”",19f));card.addView(label(v.ref,15f,true).apply{setTextColor(0xFF80643D.toInt())})
  col.addView(card)
  val saved=prefs.getStringSet("favorites",emptySet())?:emptySet()
  col.addView(btn(if(v.ref in saved)"★ Guardado en favoritos" else "☆ Guardar versículo"){
   val set=(prefs.getStringSet("favorites",emptySet())?:emptySet()).toMutableSet()
   if(v.ref in set)set.remove(v.ref) else set.add(v.ref)
   prefs.edit().putStringSet("favorites",set).apply();showHome()
  })
  col.addView(btn("📖 Leer la Biblia completa"){showReader()})
  col.addView(btn("Mis versículos favoritos"){showFavorites()})
  col.addView(label("Reina-Valera 1909 · Sin anuncios · Lectura sin conexión",13f).apply{gravity=Gravity.CENTER;setPadding(0,24,0,8);setTextColor(0xFF81796E.toInt())})
  scroll.addView(col);setContentView(scroll)
 }
 private fun showReader(){
  val box=LinearLayout(this).apply{orientation=1;setBackgroundColor(paper)}
  box.addView(btn("← Volver al inicio"){showHome()})
  val web=WebView(this).apply{
   settings.javaScriptEnabled=false;settings.allowFileAccess=true;settings.allowContentAccess=true
   webViewClient=WebViewClient();loadUrl("file:///android_asset/bible/reader.html")
  }
  box.addView(web,LinearLayout.LayoutParams(-1,0,1f));setContentView(box)
 }
 private fun showFavorites(){
  val scroll=ScrollView(this);val col=LinearLayout(this).apply{orientation=1;setPadding(24,28,24,24);setBackgroundColor(paper)}
  col.addView(label("Mis favoritos",26f,true))
  val saved=prefs.getStringSet("favorites",emptySet())?:emptySet()
  verses.filter{it.ref in saved}.forEach{v->
   col.addView(label(v.ref+"\n"+v.text,17f))
   col.addView(btn("Quitar de favoritos"){
    val set=(prefs.getStringSet("favorites",emptySet())?:emptySet()).toMutableSet();set.remove(v.ref)
    prefs.edit().putStringSet("favorites",set).apply();showFavorites()
   })
  }
  if(saved.isEmpty())col.addView(label("Todavía no guardaste versículos.",16f))
  col.addView(btn("Volver"){showHome()});scroll.addView(col);setContentView(scroll)
 }
}
