package eu.tutorials.tipcalculator

import android.os.Bundle
import android.provider.MediaStore.Images
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {

                TipCalculator()

            }
        }
    }
}

@Preview
@Composable
fun TipCalculator() {
    
    var billTotal by remember { mutableStateOf("") }
    var splitPerson by remember { mutableStateOf(1) }
    var tip by remember { mutableStateOf("") }
    var totalPerPerson by remember { mutableStateOf(0.0) }

    fun totalTip():Double{
        val inputTipDouble = tip.toDoubleOrNull() ?: 0.0
        val billTotalDouble = billTotal.toDoubleOrNull() ?: 0.0
        return billTotalDouble * (inputTipDouble/100.0)
    }

    fun totalPerPersonCal(){
        val billTotalDouble = billTotal.toDoubleOrNull() ?: 0.0
        totalPerPerson = (totalTip() + billTotalDouble) / splitPerson
    }


    Column (modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(12))
                .fillMaxHeight(0.3f)
                .fillMaxWidth()
                .background(color = Color(0xFFE9D7F7)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .offset(10.dp, 0.dp),
                text = "Total Per Person : ",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif

            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "$${"%.2f".format(totalPerPerson)}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,

                )
        }
        Column(
            modifier = Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(14.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .border(2.dp, Color.DarkGray, RoundedCornerShape(14.dp))
                .padding(32.dp)
        ){
            OutlinedTextField(
                value = billTotal,
                onValueChange = {
                    billTotal = it
                    totalPerPersonCal()
                },
                label = { Text(text = "Enter total bill",color = Color.DarkGray)},
                singleLine = true,
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Text(
                    text = "Split",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.width(64.dp))
                IconButton(onClick = {
                    splitPerson = 1
                    totalPerPersonCal()
                }) {
                    Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "Refresh Icon")
                }
                Text(
                    text = "$splitPerson",
                    fontSize = 28.sp,
                    modifier = Modifier.offset(16.dp,3.dp)
                )
                Spacer(modifier = Modifier.width(32.dp))
                IconButton(onClick = {
                    splitPerson += 1
                    totalPerPersonCal()
                }) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = "AddCircle Icon")
                }

            }
            Spacer(modifier = Modifier.height(32.dp))
            Row{
                Text(
                    text = "Tip %",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.offset(0.dp,10.dp)
                )
                Spacer(modifier = Modifier.width(32.dp))
                OutlinedTextField(
                    value = tip,
                    onValueChange = {
                        tip = it
                        totalPerPersonCal()
                    },
                    label = { Text(text = "Enter tip %",color = Color.DarkGray)},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Total tip will be $${"%.2f".format(totalTip())}",
                fontFamily = FontFamily.Serif

            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    totalPerPerson = 0.0
                    billTotal = ""
                    splitPerson = 1
                    tip = ""
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp)

            ){
                Text(text = "Reset")
            }
        }
    }
}
