package ade.yemi.growthchecker.Utilities

import ade.yemi.growthchecker.R
import ade.yemi.roomdatabseapp.Data.Challenge
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.renderscript.Int2
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.concurrent.schedule

class OnSingleClickListener(private val block: () -> Unit) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 800) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()

        block()
    }
}

fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}

fun View.zoom_in(){
    val animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    this.startAnimation(animation)
}
fun View.clicking(){
    val animation = AnimationUtils.loadAnimation(context, R.anim.click)
    this.startAnimation(animation)
}
fun View.shortvibrate() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(10)
    }
}
fun delay(time : Long){
    Timer().schedule(time) {
    }
}

class Commenntings(var state: String, var solution: String, var encourage: String){
}
fun thecomments(int: Int): Commenntings{
    var state = ""
    var solution = ""
    var encourage = ""
    when{
        int < -100 -> {state = "Your cumulative point is slightly too negative"
                        solution = "Is there a noticeable cause or reason for this? If so, put down this cause on notes at the notes page. Constantly check your notes for better reference on what to do and what to avoid."
                        encourage = "There are always setbacks, no one is perfect. Addictions can always be broken though regardless of how deep one is into it. Don't mind the state of the graph and your cumulative points, you can still raise your points and ultimately get fulfilled"}
        int < 0 && int >= -100 -> { state= "Your cumulative point is negative"
            solution = "Have you noticed any cause as to why your cumulative is negative, it would be a good idea to jot it down. Keep your notes at the notes page. Also check and update your notes for easy reference"
            encourage = "It might seem like addictions are hard to break. It all balls down to our state of mind. You can break free from anything, you are in control. Don't be discouraged. Keep trying to get your points higher by avoiding the addiction"
        }
        int == 0 ->{
            state = "Your cumulative point is zero"
            solution = "This is a neutral state. Settling and thinking of best possible things to engage in which may reduce the possibilities of indulging in the addiction could be a good idea."
            encourage = "You can still raise your points. See this as a start of the journey. Limitations only exist in the mind, it's our choice to make them our realities or not"
        }
        int > 0 && int < 100 -> {
            state = "Your cumulative point is positive"
            solution = "Are there new activities you are doing that prevent you from indulging in addictions?. Well those activities are paying off. KUDOS!!! Also, make sure you update your notes at the notes page to keep yourself in check"
            encourage = "You are on a good path. Yet there's room for improvement and proper addiction breaking"
        }
        int >= 100 && int < 300 -> {
            state = "Amazing!! Your cumulative is very positive"
            solution = "Are there by any means, helpful activities you have been engaging in which has resulted to this positive response?! Kindly put these down at the notes page for easy reference. KUDOS!!"
            encourage = "Limitations only occur in the mind, in reality there is nothing that cannot be achieved. You have done well. Getting better is also a priority, there is room for improvement"
        }
        int >= 300 ->{
            state = "Very impressive!! Your cumulative is far positive "
            solution = "Definitely!! You are at the best point of addiction breaking now. Are there helpful activities you do that enable you stick to doing what is deemed right? Kindly put these down at the notes page for easy reference"
            encourage = "Absolutely impressive"
        }

    }
    var commenntings : Commenntings = Commenntings(state, solution, encourage)
    return commenntings
}
fun generatecumulative(stuff: MutableList<String>):String{
    var list1 = mutableListOf<Int>()
    var num1 = 0
    var shown2 = ""
    for (i in stuff){
        num1 = num1 + i.toInt()
        list1.add(num1)
    }
    for (i in list1){
        shown2 = "$shown2 $i \n\n"
    }
    return shown2
}
fun generatepoints(stuff: MutableList<String>) :String{
    var shown = ""
    for(i in stuff){
        shown = "$shown $i \n\n"
    }
    return shown
}
fun generatedays(stuff: MutableList<String>) :String{
    var shown = ""
    for (i in 1..stuff.size){
        shown = "$shown $i \n\n"
    }
    return  shown
}
class awardpagepointsearned(var attained: Int, var attainable : Int ){}
fun getpoints(points: MutableList<String>, challenge: Challenge) : awardpagepointsearned{
    var attained = 0

    for (i in points){
        attained = attained + i.toInt()
    }
    var attainable = challenge.Days.toInt() * 22
    return awardpagepointsearned(attained, attainable)
}

fun setranking(attained: Int, attainable: Int):String{
    var word = ""
    when{
        attained < (attainable / 7) -> word ="Fifth rank attained. No award for this rank"
        attained >= (attainable /7) && attained < (attainable/4.5) -> word ="Fourth rank attained. No award for this rank"
        attained >= (attainable /4.5) && attained < (attainable/2) -> word ="Third rank attained. Nice attempt!"
        attained >= (attainable /2) && attained < (attainable/1.3) -> word ="Second rank attained. Great Job"
        attained >= (attainable /1.3) && attained <= attainable-> word ="You attained the highest rank. Congratulations!!"
        attained > attainable -> word ="You attained the highest rank. Congratulations!!"
    }
    return word
}

fun setrankimage(attained: Int, attainable: Int, days: String, imageView: ImageView){
    var rank = 0
    when{
        attained < attainable / 7 -> rank = 5
        attained >= attainable /7 && attained < attainable/4.5 -> rank =4
        attained >= attainable /4.5 && attained < attainable/2 -> rank =3
        attained >= attainable /2 && attained < attainable/1.3 -> rank =2
        attained >= attainable /1.3 && attained <= attainable-> rank =1
        attained > attainable -> rank = 1
    }

    when{
        days == "14" && rank == 1 -> imageView.setImageResource(R.drawable.firstchallengefirst)
        days == "14" && rank == 2 -> imageView.setImageResource(R.drawable.firstchallengesecond)
        days == "14" && rank == 3 -> imageView.setImageResource(R.drawable.firstchallengethird)
        days == "14" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none1)

        days == "30" && rank == 1 -> imageView.setImageResource(R.drawable.secondchallengefirst)
        days == "30" && rank == 2 -> imageView.setImageResource(R.drawable.secondchallengesecond)
        days == "30" && rank == 3 -> imageView.setImageResource(R.drawable.secondchallengethird)
        days == "30" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none2)

        days == "60" && rank == 1 -> imageView.setImageResource(R.drawable.thirdchallengefirst)
        days == "60" && rank == 2 -> imageView.setImageResource(R.drawable.thirdchallengesecond)
        days == "60" && rank == 3 -> imageView.setImageResource(R.drawable.thirdchallengethird)
        days == "60" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none3)

        days == "100" && rank == 1 -> imageView.setImageResource(R.drawable.fourthchallengefirst)
        days == "100" && rank == 2 -> imageView.setImageResource(R.drawable.fourthchallengesecond)
        days == "100" && rank == 3 -> imageView.setImageResource(R.drawable.fourthchallengethird)
        days == "100" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none4)

        days == "200" && rank == 1 -> imageView.setImageResource(R.drawable.fifthchallengefirst)
        days == "200" && rank == 2 -> imageView.setImageResource(R.drawable.fifthchallengesecond)
        days == "200" && rank == 3 -> imageView.setImageResource(R.drawable.fifthchallengethird)
        days == "200" && (rank == 4 || rank == 5) -> imageView.setImageResource(R.drawable.none5)
    }
}

class profileimages(var image: ImageView, var num: Int)
fun setimage(imageView: ImageView, int: Int){
    when(int){
        1 -> imageView.setImageResource(R.drawable.a40)
        2 -> imageView.setImageResource(R.drawable.a41)
        3 -> imageView.setImageResource(R.drawable.a42)
        4 -> imageView.setImageResource(R.drawable.a43)
        5 -> imageView.setImageResource(R.drawable.a44)
        6 -> imageView.setImageResource(R.drawable.a45)
        7 -> imageView.setImageResource(R.drawable.a46)
        8 -> imageView.setImageResource(R.drawable.a47)
        9 -> imageView.setImageResource(R.drawable.a48)
        10 -> imageView.setImageResource(R.drawable.a49)
        11 -> imageView.setImageResource(R.drawable.a50)
        12 -> imageView.setImageResource(R.drawable.a51)
        13 -> imageView.setImageResource(R.drawable.a53)
        14 -> imageView.setImageResource(R.drawable.a54)
        15 -> imageView.setImageResource(R.drawable.a55)
        16 -> imageView.setImageResource(R.drawable.a56)

        17 -> imageView.setImageResource(R.drawable.a1)
        18 -> imageView.setImageResource(R.drawable.a2)
        19 -> imageView.setImageResource(R.drawable.a3)
        20 -> imageView.setImageResource(R.drawable.a4)
        21 -> imageView.setImageResource(R.drawable.a5)
        22 -> imageView.setImageResource(R.drawable.a6)
        23 -> imageView.setImageResource(R.drawable.a7)
        24 -> imageView.setImageResource(R.drawable.a8)
        25 -> imageView.setImageResource(R.drawable.a9)
        26 -> imageView.setImageResource(R.drawable.a10)
        27 -> imageView.setImageResource(R.drawable.a11)
        28 -> imageView.setImageResource(R.drawable.a12)
        29 -> imageView.setImageResource(R.drawable.a13)
        30 -> imageView.setImageResource(R.drawable.a14)
        31 -> imageView.setImageResource(R.drawable.a15)
        32 -> imageView.setImageResource(R.drawable.a16)


        33 -> imageView.setImageResource(R.drawable.a17)
        34 -> imageView.setImageResource(R.drawable.a18)
        35 -> imageView.setImageResource(R.drawable.a19)
        36 -> imageView.setImageResource(R.drawable.a20)
        37 -> imageView.setImageResource(R.drawable.a21)
        38 -> imageView.setImageResource(R.drawable.a22)
        39 -> imageView.setImageResource(R.drawable.a23)
        40 -> imageView.setImageResource(R.drawable.a24)
        41 -> imageView.setImageResource(R.drawable.a25)
        42 -> imageView.setImageResource(R.drawable.a26)
        43 -> imageView.setImageResource(R.drawable.a27)
        44 -> imageView.setImageResource(R.drawable.a28)
        45 -> imageView.setImageResource(R.drawable.a29)
        46 -> imageView.setImageResource(R.drawable.a30)
        47 -> imageView.setImageResource(R.drawable.a31)
        48 -> imageView.setImageResource(R.drawable.a32)

        49 -> imageView.setImageResource(R.drawable.a33)
        50 -> imageView.setImageResource(R.drawable.a34)
        51 -> imageView.setImageResource(R.drawable.a35)
        52 -> imageView.setImageResource(R.drawable.a36)
        53 -> imageView.setImageResource(R.drawable.a37)
        54 -> imageView.setImageResource(R.drawable.a38)
        55 -> imageView.setImageResource(R.drawable.a39)



        else -> imageView.setImageResource(R.drawable.a40)
    }
}

fun levelimage(OverallScore: Int, image: ImageView, comment: TextView){
    when{
       OverallScore < 0  -> {image.setImageResource(R.drawable.l1)
           comment.text = "Still Moving"}
        OverallScore >=1 && OverallScore < 200 -> {image.setImageResource(R.drawable.l2)
            comment.text = "Amazing!!"}
        OverallScore >=200 && OverallScore < 1000  -> {image.setImageResource(R.drawable.l3)
            comment.text = "Superb!!!"}
        OverallScore >=1000 && OverallScore < 5000  -> {image.setImageResource(R.drawable.l4)
            comment.text = "Wonderful!!"}
        OverallScore >=5000 && OverallScore < 15000  -> {image.setImageResource(R.drawable.l5)
            comment.text = "You are a CHAMPION!!!"}
        OverallScore >=15000 -> {image.setImageResource(R.drawable.l6)
            comment.text = "Congratulations CHAMPION!!!"}
    }
}

class Addiction(var addiction: String, var overview: String, var symptoms:String, var causes:String, var effects: String, var Daily: String)
fun Addictionlist():List<Addiction>{
    var list = listOf<Addiction>(
            Addiction(addiction = "Hypersexuality",
                    overview = "Compulsive sexual behaviour is sometimes called hypersexuality, hypersexuality disorder or sexual addiction. It's an excessive preoccupation with sexual fantasies, urges or behaviours that are difficult to control, causes you distress, or negatively affects your health, job, relationships or other parts of your life. \nCompulsive sexual behaviour may involve a variety of commonly enjoyable sexual experiences. \nExamples include masturbation, cybersex, multiple sexual partners, use of pornography or paying for sex. When these sexual behaviours become a major focus in your life, are difficult to control, and are disruptive or harmful to you or others, they may be considered compulsive sexual behaviour.\n\nNo matter what it's called or the exact nature of the behaviour, untreated compulsive sexual behaviour can damage your self-esteem, relationships, career, health and other people. But with treatment and self-help, you can learn to manage compulsive sexual behaviour.",
                    symptoms = "1.  You have recurrent and intense sexual fantasies, urges and behaviours that take up a lot of your time and feel as if they're beyond your control.\n\n2.  You feel driven to do certain sexual behaviours, feel a release of the tension afterward, but also feel guilt or remorse.\n\n3.  You've tried unsuccessfully to reduce or control your sexual fantasies, urges or behaviour. \n\n4.  You use compulsive sexual behaviour as an escape from other problems, such as loneliness, depression, anxiety or stress. \n\n5.  You continue to engage in sexual behaviours that have serious consequences, such as the potential for getting or giving someone else a sexually transmitted infection, the loss of important relationships, trouble at work, financial strain, or legal problems. \n\n6.  You have trouble establishing and maintaining healthy and stable relationships.",
                    causes = "Although the causes of compulsive sexual behaviour are unclear, they may include: \n\n1. An imbalance of natural brain chemicals:\nCertain chemicals in your brain (neurotransmitters) such as serotonin, dopamine and norepinephrine help regulate your mood. High levels may be related to compulsive sexual behaviour. \n\n2. Changes in brain pathways:\nCompulsive sexual behaviour may be an addiction that, over time, might cause changes in the brain's neural circuits, especially in the reinforcement centres of the brain. Like other addictions, more-intensive sexual content and stimulation are typically required over time in order to gain satisfaction or relief. \n\n3. Conditions that affect the brain:\nCertain diseases or health problems, such as epilepsy and dementia, may cause damage to parts of the brain that affect sexual behaviour. In addition, treatment of Parkinson's disease with some dopamine agonist medications may cause compulsive sexual behaviour.",
                    effects = "Compulsive sexual behaviour can have many negative consequences that affect both you and others. You may: \n\n1. Struggle with feelings of guilt, shame and low self-esteem. \n\n2. Develop other mental health conditions, such as depression, suicide, severe distress and anxiety. \n\n3. Neglect or lie to your partner and family, harming or destroying meaningful relationships. \n\n4. Lose your focus or engage in sexual activity or search internet pornography at work, risking your job. \n\n5. Accumulate financial debts buying pornography and sexual services. \n\n6. Contract HIV, hepatitis or another sexually transmitted infection or pass a sexually transmitted infection to someone else. \n\n7. Engage in unhealthy substance use, such as using recreational drugs or drinking excessive alcohol. \n\n8. Be arrested for sexual offenses. \n\n",
                    Daily = "1. Abstinence.\n\n2.  Avoiding sexual temptations like pornography.\n\n3.  Avoid company of sex addicts.\n\n4.  Reading developmental materials, religious books (Eg Bible, Quran )\n\n5.  Staying around trusted non sexual addicts.\n\n6.  Practice life changing habits Eg. listening to motivation speakers, sermons etc.\n\n7. Seek help from professionals and apply professional advices daily."),
            Addiction(addiction = "Pornography",
                    overview = "Porn addiction refers to a person becoming emotionally dependent on pornography to the point that it interferes with their daily life, relationships, and ability to function. \nThis type of addiction may be quite common. Some doctors consider porn addiction to be a hypersexual disorder, an umbrella term that includes behaviours such as excessive masturbation.",
                    symptoms = "1. They ignore other responsibilities to view pornography. \n\n2. They view progressively more extreme pornography to get the same release that less extreme porn once offered. \n\n3. They feel frustrated or ashamed after viewing porn but continue to do so. \n\n4. They want to stop using pornography but feel unable to do so. \n\n5. They spend large sums of money on pornography, possibly at the expense of daily or family necessities. \n\n6. They use pornography to cope with sadness, anxiety, insomnia, or other mental health issues.",
                    causes = "Some of the causes of pornography addiction may include: \n\n1. Underlying mental health conditions: \nA person might use pornography to escape psychological distress. \n\n2. Relationship problems: \nPornography can be an outlet for sexual dissatisfaction. \n\n3. Unhealthy cultural norms: \nIdeas about how people should look and behave during sex, the types of sex that a person should enjoy, and similar norms may draw some people to pornography. \n\n4. Biological causes: \nCertain biological factors, including changes in brain chemistry when a person views porn, may increase the risk of addiction. \n\n",
                    effects = "The negative effects of pornography include the following; \n\n1. Pornography causes relationship problems. \n\n2. A person feels guilty about their pornography use. \n\n3. A person wants to cut down on their pornography use but feels unable to do so. \n\n",
                    Daily = "1. Avoid visiting pornography websites.\n\n2. Avoiding sexual temptations.\n\n3. Avoiding company of porn addicts.\n\n4.  Reading developmental materials.\n\n5.  Staying around trusted non sexual addicts."),

            Addiction(addiction = "Self-Harming",
                    overview = "Cutting and other forms of self-harm are types of medical illnesses. These occur when patients hurt themselves to help deal with deep-set emotions or distress. The prevalence of intentional self-injury is increasing.\nUnderstanding the disease can help you or your loved one recognize the signs and get help. Proper and swift treatment is the best way to prevent self-harm from becoming fatal.",
                    symptoms = "When any of the following is noticed, the bearer is probably self-harming; \n\n1. Scars\n\n2. Fresh cuts, scratches, bruises, bite marks etc \n\n3. The excessive tugging of the skin. \n\n4. Always holding sharp objects. \n\n5. even in hot weather, wearing long pants or long sleeve. \n\n6. Frequent accidental injury. \n\n7. Little or no interpersonal relationships. \n\n8. Behavioural and unpredictability, impulsivity, and emotional instability. \n\n9. Making statements of worthlessness or helplessness.",
                    causes = "Generally, Causes of Self-Harm May Come From: \n\n1. Poor Coping Skills: \nNon-suicidal self-harm usually results from the inability to cope with psychological pain in healthy ways. \n\n2. Difficulty Managing Emotions: \nThis is when a person has difficulty regulating or expressing and grasping emotions. The combination of feelings that trigger self-injury can be complicated. Among other things, it may be a feeling of worthlessness.",
                    effects = "Although a person may think cutting will help, the practice actually aggravates the person’s feelings of shame or low self-esteem.\n1. Cutting only temporarily resolves the negative emotions and does nothing to change longstanding emotional issues.\n2. Cutting also increases the risk of developing infection, either from the self-inflicted wounds or from sharing cutting tools.\n3. Most cutters do not practice proper hygiene and can contract infectious diseases from contaminated cutting implements. There are some reports of cutters dying after contracting an infection due to their cutting habits.\n4. If a major artery or vein is cut, the individual is at risk of bleeding to death. Frequent bloodletting also leads to anaemia, which can damage other organs within the body. The risk of suicide (whether accidental or deliberate) is increased in people who cause self-harm. In cases of accidental suicide, the patient unintentionally cuts a major blood vessel. To make matters worse, these patients often cut themselves while under the influence of alcohol or other drugs, increasing the risk of suicide.\n5. Permanent scars or horrible disfigurement are also possible complications of cutting. In rare cases, the cutter will destroy a vital tendon or nerve and thus lose the ability to control their hands or legs. And sometimes the paralysis is permanent. In long-term cases of cutting, the behaviour becomes a compulsion. Cutters will seek out times and areas where they can cut themselves. Even if they don’t want to, they have become addicted to the cutting behaviour.",
                    Daily = "1. Avoid negative thoughts.\n\n2. Stay in the company of those who encourage you.\n\n3. Avoid depression. Search around for things that make you happy.\n\n4. Find support groups.\n\n5. Engage in other enjoyable activities. Eg Football\n\n6. Focus on the positive and encourage yourself.\n\n7. Avoid the temptation.\n\n8.  Seek help from professionals and apply professional advices daily."),

            Addiction(addiction = "Gambling",
                    overview = "Gambling addiction is the uncontrollable urge to continue gambling despite the toll it takes on one’s life. Gambling is addictive because it stimulates the brain’s reward system much like drugs or alcohol can. In fact, gambling addiction is the most common impulse control disorder worldwide.",
                    symptoms = "Signs and symptoms of compulsive gambling (gambling disorder) include: \n\n1. Being preoccupied with gambling, such as constantly planning how to get more gambling money. \n\n2. Needing to gamble with increasing amounts of money to get the same thrill. \n\n3. Trying to control, cut back or stop gambling, without success. \n\n4. Feeling restless or irritable when you try to cut down on gambling. \n\n5. Gambling to escape problems or relieve feelings of helplessness, guilt, anxiety or depression. \n\n6. Trying to get back lost money by gambling more (chasing losses). \n\n7. Lying to family members or others to hide the extent of your gambling. \n\n8. Jeopardizing or losing important relationships, a job, or school or work opportunities because of gambling. \n\n9. Resorting to theft or fraud to get gambling money. \n\n10. Asking others to bail you out of financial trouble because you gambled money away.",
                    causes = "Although most people who play cards or wager never develop a gambling problem, certain factors are more often associated with compulsive gambling:\n1. Mental health disorders:\n People who gamble compulsively often have substance abuse problems, personality disorders, depression or anxiety. Compulsive gambling may also be associated with bipolar disorder, obsessive-compulsive disorder (OCD) or attention-deficit/hyperactivity disorder (ADHD). \n\n2. Age: \nCompulsive gambling is more common in younger and middle-aged people. Gambling during childhood or the teenage years increases the risk of developing compulsive gambling. However, compulsive gambling in the older adult population can also be a problem. \n\n3. Gender:\nCompulsive gambling is more common in men than women. Women who gamble typically start later in life and may become addicted more quickly. But gambling patterns among men and women have become increasingly similar. \n\n4. Family or friend influence: \nIf your family members or friends have a gambling problem, the chances are greater that you will, too. \n\n5. Medications used to treat Parkinson's disease and restless legs syndrome: \nDrugs called dopamine agonists have a rare side effect that may result in compulsive behaviours, including gambling, in some people. \n\n6. Certain personality characteristics: \nBeing highly competitive, a workaholic, impulsive, restless or easily bored may increase your risk of compulsive gambling.",
                    effects = "Compulsive gambling can have profound and long-lasting consequences for your life, such as: \n\n1. Relationship problems. \n\n2. Financial problems, including bankruptcy. \n\n3. Legal problems or imprisonment. \n\n4. Poor work performance or job loss. \n\n5. Poor general health. \n\n6. Suicide, suicide attempts or suicidal thoughts. \n\n",
                    Daily = "1. Avoid gambling centres, apps and the likes.\n\n2. Work on your immediate business plans that do not involve gambling.\n\n3. Spend money wisely.\n\n4. Join a support group and communicate daily.\n\n5. Avoid gambling temptations.\n\n6. Seek help from professionals and apply professional advices daily.\n\n"),

            Addiction(addiction = "Internet",
                    overview = "Internet addiction is an umbrella term that refers to the compulsive need to spend a great deal of time on the Internet, to the point where relationships, work and health are allowed to suffer.",
                    symptoms = "Internet addiction can include three or more of the following:\n1. The user needs to spend ever-increasing amounts of time online to feel the same sense of satisfaction. \n\n2. If they can’t go online, the user experiences unpleasant withdrawal symptoms such as anxiety, moodiness and compulsive fantasising about the Internet. Using the Internet relieves these symptoms. \n\n3. The user turns to the Internet to cope with negative feelings such as guilt, anxiety or depression. \n\n4. The user spends a significant amount of time engaging in other activities related to the Internet (such as researching internet vendors, internet books). \n\n5. The user neglects other areas of life (such as relationships, work, school and leisure pursuits) in favour of spending time on the Internet. \n\n6. The user is prepared to lose relationships, jobs or other important things in favour of the Internet.",
                    causes = "There’s no single cause of internet addiction. Several factors can play a role, including:\n1. Underlying mental health conditions, including anxiety and depression. \n\n2. Genetics. \n\n3. Environmental factors.",
                    effects = "An internet addiction can have many harmful effects on a person, both physically and emotionally. Body aches, Carpal Tunnel Syndrome, insomnia, vision problems, and weight gain/loss are just some of the physical problems one may suffer as a result of an internet addiction. Emotional effects may include depression, dishonesty, anxiety, social isolation, aggression, and mood swings.",
                    Daily = "1.  Set daily limit for use of internet and stick to it.\n\n2.  keep devices inaccessible.\n\n3.  Find activities outside.\n\n4.  Subscribe lesser for internet connection.\n\n5.  join a support group and communicate daily.\n\n6.  Seek help from professionals and apply professional advices daily."),

            Addiction(addiction = "Food",
                    overview = "A food addiction or eating addiction is a behavioural addiction that is characterized by the compulsive consumption of palatable (e.g., high fat and high sugar) foods which markedly activate the reward system in humans and other animals despite adverse consequences.",
                    symptoms = "This Eating Disorder can be recognizable by numerous signs and symptoms.  The following are possible symptoms of an addiction to food:\n1. Gorging in more food than one can physically tolerate. \n\n2. Eating to the point of feeling ill. \n\n3. Going out of your way to obtain certain foods. \n\n4. Continuing to eat certain foods even if no longer hungry. \n\n5. Eating in secret, isolation. \n\n6. Avoiding social interactions, relationships, or functions to spend time eating certain foods. \n\n7. Difficulty function in a career or job due to decreased efficiency. \n\n8. Spending a significant amount of money on buying certain foods for bingeing purposes. \n\n9. Decreased energy, chronic fatigue. \n\n10. Difficulty concentrating. \n\n11. Sleep disorders, such as insomnia or oversleeping. \n\n12. Restlessness. \n\n13. Irritability. \n\n14. Headaches. \n\n15. Digestive disorders. \n\n16. Suicidal ideations.",
                    causes = "Food addiction has many suspected causes. Research has shown that addiction has a genetic component, but other factors may cause food addiction. \n\n1. Emotions and stress: \nPeople who become addicted to food may eat to enhance positive emotions and to reduce negative emotions.5 For example, you might eat a pizza to reward yourself for an accomplishment, but you might also eat a pizza because something bad happened and you deserve it. This is classic addictive thinking.2. Brain chemistry: \nFoods that are rich in fat and sugar can alter the reward centres of the brain in the same way as drugs and alcohol. The presence of high-sugar foods reduced self-administration of cocaine and heroin in rats trained to press a lever to receive intravenous drugs. \n\n3. Genetics: \nAnother food addiction cause may be genetics. A 2002 study found that women with a family history of alcoholism had a 49% higher chance of obesity than those without a family history.6 Although not everyone with obesity has a food addiction, this suggests that there may be a relationship between alcohol addiction and food addiction. \n\n4. Trauma: \nA study of women with PTSD found that women with the greatest number of PTSD symptoms had more than twice the prevalence of food addiction as women with no PTSD symptoms or no history of trauma. When the trauma and onset of PTSD symptoms happened at an earlier age, the relationship to food addiction was even stronger.  This suggests that women who experienced a childhood trauma may be at greater risk of food addiction. ",
                    effects = "Anyone who has binged on food knows the physical short-term effects of food addiction. These can be very uncomfortable and can include:\n1. Upset stomach. \n\n2. Heartburn. \n\n3. Extreme nausea. \n\n4. Vomiting.",
                    Daily = "1.  Mind how you eat daily.\n\n2. Exercise regularly.\n\n3. Avoid alcohols and caffeine.\n\n4. Make a list of junks to stop eating and don't eat them daily.\n\n5. Seek help from professionals and apply professional advices daily."),

            Addiction(addiction = "Drug Addiction",
                    overview = "Drug abuse is when you use legal or illegal substances in ways you shouldn’t. \nYou might take more than the regular dose of pills or use someone else’s prescription. \nYou may abuse drugs to feel good, ease stress, or avoid reality. But usually, you’re able to change your unhealthy habits or stop using altogether.\n\nWhen you’re addicted to drugs, you can’t resist the urge to use them, no matter how much harm the drugs may cause. The earlier you get treatment for drug addiction, the more likely you are to avoid some of the more dire consequences of the disease.\n\nDrug addiction isn’t about just heroin, cocaine, or other illegal drugs. You can get addicted to alcohol, nicotine, sleep and anti-anxiety medications, and other legal substances.\nYou can also get addicted to prescription or illegally obtained narcotic pain medications, or opioids. At first, you may choose to take a drug because you like the way it makes you feel. You may think you can control how much and how often you use it. But over time, drugs change how your brain works. These physical changes can last a long time. They make you lose control and can lead to damaging behaviors.",
                    symptoms = "Many forms of drug abuse come with small behavioral changes that might be dismissed as 'tics'. If you notice any of the following, it could be signs of a hidden condition: \n\n1. Persistent itching in a specific area of the body, Impulsive pulling down of sleeves to hide marks, Slurred speech, Frequent sniffling and so on.\n\n2. Long-term abuse of drugs and alcohol can result in drastic changes to physical appearance. \n\n3. Many drugs have appetite suppressing or other altering side effects, meaning abuse often results in visible weight changes.\n\n4. These rapid changes to body composition, such as sudden weight loss or weight gain, or lack of interest in personal grooming, especially if it declines without explanation, can also point to substance abuse and can be cause for concern.",
                    causes = "Causes of Substance Use Disorders may include: \n\n1. Family history of addiction. \n\n2. Sleep problems. \n\n3. Chronic pain. \n\n4. Financial difficulties. \n\n5. Divorce or the loss of a loved one. \n\n6. Long-term tobacco habit. \n\n7. Tense home environment. \n\n8. Lack of parental attachment in childhood.",
                    effects = "Some of the negative effects of drug abuse are:\n\n1. A weakened immune system, increasing the risk of illness and infection.\n\n2. Heart conditions ranging from abnormal heart rates to heart attacks and collapsed veins and blood vessel infections from injected drugs.\n\n3. Nausea and abdominal pain, which can also lead to changes in appetite and weight loss.\n\n4. Increased strain on the liver, which puts the person at risk of significant liver damage or liver failure.\n\n5. Seizures, stroke, mental confusion and brain damage.\n\n6. Lung disease.\n\n7. Problems with memory, attention and decision-making, which make daily living more difficult.\n\n8. Global effects of drugs on the body, such as breast development in men and increases in body temperature, which can lead to other health problems.",
                    Daily = "1. Avoid hanging around drug addicts.\n\n2. Avoid daily temptations.\n\n3. Do away with harmful drugs daily and only use drugs prescribed by doctors.\n\n4. Join a support group and communicate daily.\n\n5. Attend seminars and programs relating to dealing with drug addiction.\n\n6. Read developmental materials.\n\n7. Seek professional help and apply professional advices daily."),
            Addiction(addiction = "Alcoholism",
                    overview = "Alcohol addiction, also known as alcoholism. It is an addiction that affects people of all walks of life. Experts have tried to pinpoint factors like genetics, sex, race, or socioeconomics that may predispose someone to alcohol addiction. But it has no single cause. Psychological, genetic, and behavioral factors can all contribute to having the addiction.\n\nIt’s important to note that alcoholism is a seious addiction. It can cause changes to the brain and neurochemistry, so a person with an alcohol addiction may not be able to control their actions.\n\nAlcohol addiction can show itself in a variety of ways. The severity of the disease, how often someone drinks, and the alcohol they consume varies from person to person. Some people drink heavily all day, while others binge drink and then stay sober for a while.\n\nRegardless of how the addiction looks, someone typically has an alcohol addiction if they heavily rely on drinking and can’t stay sober for an extended period of time.",
                    symptoms = "Alcohol addiction can be difficult to recognize. Unlike cocaine or heroin, alcohol is widely available and accepted in many cultures. It’s often at the center of social situations and closely linked to celebrations and enjoyment.\n\nDrinking is a part of life for many people. When is it common in society, it can be hard to tell the difference between someone who likes to have a few drinks now and then and someone with a real problem.\n\nSome symptoms of alcohol addiction are:\n\n1. Increased quantity or frequency of use.\n\n2. High tolerance for alcohol, or lack of hangover symptoms.\n\n3. Drinking at inappropriate times, such as first thing in the morning, or in places like church or work.\n\n4. Wanting to be where alcohol is present and avoiding situations where there is none.\n\n5. Changes in friendships; someone with an alcohol addiction may choose friends who also drink heavily.\n\n6. Avoiding contact with loved ones.\n\n7. Hiding alcohol, or hiding while drinking.\n\n8. Dependence on alcohol to function in everyday life.\n\n9. Increased lethargy, depression, or other emotional issues.\n\n10. Legal or professional problems such as an arrest or loss of a job.",
                    causes = "1. The cause of alcohol use disorder is still unknown. Alcohol use disorder develops when you drink so much that chemical changes in the brain occur. These changes increase the pleasurable feelings you get when you drink alcohol. This makes you want to drink more often, even if it causes harm. \n\n2. Eventually, the pleasurable feelings associated with alcohol use go away and the person with alcohol use disorder will engage in drinking to prevent withdrawal symptoms. These withdrawal symptoms can be quite unpleasant and even dangerous. \n\nAlcohol use disorder typically develops gradually over time. It’s also known to run in families.",
                    effects = "Alcohol addiction can result in heart disease and liver disease. Both can be fatal. Alcoholism can also cause:\n\n1. Ulcers\n\n2. Diabetes complications\n\n3. Sexual problems\n\n4. Birth defects\n\n5. Bone loss\n\n6. Vision problems\n\n7.Increased risk of cancer.\n\n8. Suppressed immune function.\n\nThese complications are reasons why it’s important to treat alcohol addiction early. Nearly all risks involved with alcohol addiction may be avoidable or treatable, with successful long-term recovery.",
                    Daily = "1. Avoid the company of alcohol addicts.\n\n2. Avoid temptation to drink.\n\n3. Constantly clear alcohol products out of sight.\n\n4. Join a support group and communicate daily.\n\n5. Stay around non alcohol addicts.\n\n6. Seek professional help and apply professional advices daily."),

            Addiction(addiction = "Smoking",
                    overview = "Many people believe that smoking a cigar is safer than smoking a cigarette. But, cigar smokers face many of the same potential risks as cigarette smokers, including cancer.\nChewing tobacco or smokeless tobacco products are not safer than cigarettes, either. Smokeless tobacco contains almost 30 cancer-causing chemicals.\n\nE-cigarettes (vapes), an emerging form of nicotine delivery, differ from traditional tobacco products. Vaping delivers more concentrated nicotine  than cigarettes in a smokeless inhaled mist (vapor). \nHealth risks from vape products range from asthma to chronic obstructive pulmonary disease and cancer.",
                    symptoms = "Symptoms of smoking and related diseases, disorders and conditions include:\n\n1. Bad breath and yellowing of the teeth.\n\n2. Cold hands and feet.\n\n3. Frequent or recurrent lung infections and other diseases, such as influenza, common colds, bronchitis, and pneumonia.\n\n4. Hypertension (high blood pressure) and rapid heart rate\n\n5. Loss of taste and smell.\n\n6. Low oxygen levels in the blood.\n\n7. Low tolerance for exercise and fatigue.\n\n8. Nicotine-stained fingers and teeth.\n\n9. Premature aging and wrinkling of the skin.\n\n10. Shortness of breath and difficulty breathing.\n\n11. Smoker's cough (an ongoing loose cough that produces phlegm) and hoarse voice.\n\n12. Smoky-smelling clothes and hair.",
                    causes = "Some of the reasons people smoke cigarettes: \n\n1. Peer Pressure. \n\n2. Parental Influence. \n\n3. Media Influence. \n\n4. Genetic Factors. \n\n5. Stress Factors. ",
                    effects = "Tobacco use harms every organ in your body. Smoking tobacco introduces not only nicotine but also more than 5,000 chemicals, including numerous carcinogens (cancer-causing chemicals), into your lungs, blood and organs.\nThe damage caused by smoking can shorten your lifespan significantly.\nPregnant women who smoke put their unborn babies at risk, too. Possible effects on pregnancy include:\n\n1. Ectopic pregnancy, a life-threatening condition when the embryo implants outside the uterus.\n\n2. Miscarriages.\n\n3. Stillbirths.\n\n4. Birth defects, such as cleft palate.\n\n5. Low birth weight.",
                    Daily = "1. Avoid the temptation.\n\n2. Get a non-smoking support group and interact daily.\n\n3. Stay around non-smokers.\n\n4. Engage in life changing activities whose environments do not support smoking.\n\n5. Seek professional help and apply professional advices daily.")
    )
    return list
}
fun typecolour(cardView: CardView, string: String){
    when(string){
        "Encouragement" -> cardView.setCardBackgroundColor(Color.parseColor("#95A600"))
        "Congratulatory" -> cardView.setCardBackgroundColor(Color.parseColor("#14C600"))
        "Achievement" -> cardView.setCardBackgroundColor(Color.parseColor("#00B4F6"))
        "Warning" -> cardView.setCardBackgroundColor(Color.parseColor("#FFE26D"))
        "Relapse" -> cardView.setCardBackgroundColor(Color.parseColor("#D10000"))
    }
}


