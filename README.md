# petview
自定义一个View里面是饼图，先看看效果吧；
![image]https://raw.githubusercontent.com/softwareboy92/petview/master/device-2017-03-09-154736.png

#####在Activity中布局就可以这样布局了；
######<com.lvndk.barnner.view.PieView
######android:id="@+id/pie_view"
######android:layout_width="match_parent"
######android:layout_height="wrap_content"
######app:color_a="#d2f6f2"
######app:color_b="#fe8081"
######app:color_c="#ffdf6e"
######app:color_d="#afa9ff"
######app:percent_arc_a="100"
######app:stroke_width="32dp"
######app:text="70"
######app:text_color="#333333"
######app:text_percent_size="14sp"
######app:text_size="50sp" />
#####这样放在你的布局中就看起来挺好的；
#####然后接下来我们要在主要的Activity中调用了，调用方法如下；
 
#####//这些是必须设置的，具体的代码在上面都有详细的注释和讲解啦，不废话了<br/>
mPieView = (PieView) findViewById(R.id.pie_view);<br/>
mPieView.setArcA(60);<br/>
mPieView.setArcB(10);<br/>
mPieView.setArcC(20);<br/>
mPieView.setArcD(10);<br/>
mPieView.setText("60");<br/>
mPieView.setTextSize(50);<br/>
mPieView.setTextTips("收入");<br/>
mPieView.setTextPercentSize(16);<br/>

#####//这下面的是可以不设置的，但是在xml文件中要设置，所以动态设置和惊天设置这个看你心情；<br/>
mPieView.setColorA(Color.RED);<br/>
mPieView.setColorB(Color.YELLOW);<br/>
mPieView.setColorC(Color.GREEN);<br/>
mPieView.setColorD(Color.BLUE);<br/>
#####//动画效果的启动<br/>
mPieView.startAnim();<br/>
</br>
</br>
#####就这样这个就是简单的饼图，当我们饿了的时候我们可以吃点，哈哈，不搞笑了，继续研究<br/>
