package com.example.moremovies.screen.filter_film_screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moremovies.R
import com.example.moremovies.utils.constant.YEAR_FROM
import com.example.moremovies.utils.constant.YEAR_TO
import com.example.moremovies.navigation.MainRoute
import com.example.moremovies.network.model_request.film.FilmsRequest
import com.example.moremovies.network.model_request.film.LocalizedName
import com.example.moremovies.network.model_request.film.MovieType
import com.example.moremovies.network.model_request.film.OrderTypeForFilter
import com.example.moremovies.screen.fitures.CustomAppBar
import com.example.moremovies.screen.fitures.GradientButton
import com.example.moremovies.ui.theme.MainColor
import com.example.moremovies.ui.theme.Orange
import com.example.moremovies.ui.theme.Red
import com.example.moremovies.ui.theme.WhiteColor
import com.example.moremovies.ui.theme.styleTextBodyNormal
import com.example.moremovies.ui.theme.styleTextBodyBig
import com.google.gson.Gson

import kotlin.math.roundToInt

@Composable
fun FilterFilmScreen(
    navigationController: NavHostController,
    filmsRequest: FilmsRequest,
) {
    val values = LocalContext.current
    val copyFilmRequest = filmsRequest.copy()

    if (filmsRequest.keyword != null) {
        copyFilmRequest.keyword = null
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = null,
                title = values.getString(R.string.filter),
                onNavigate = {
                    navigationController.navigateUp()
                })
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainColor)
                .padding(paddingValues)
        ) {


            val orderTypeValues =
                OrderTypeForFilter.entries.map { it to it as LocalizedName }.toTypedArray()

            ChooseType(
                currentValue = mutableStateOf(filmsRequest.order),
                onValueChange = { filmsRequest.order = it },
                allValues = orderTypeValues,
                labelText = values.getString(R.string.filterChooseFilm)
            )

            val movieTypeValues = MovieType.entries.map { it to it as LocalizedName }.toTypedArray()

            ChooseType(
                currentValue = mutableStateOf(filmsRequest.type),
                onValueChange = { filmsRequest.type = it },
                allValues = movieTypeValues,
                labelText = values.getString(R.string.filterChooseTypeFilm)
            )

            SliderWithIntValue(
                filmsRequest.ratingFrom,
                filmsRequest.ratingTo
            ) { roundedRatingTo, roundedRatingFrom ->
                filmsRequest.ratingFrom = roundedRatingTo
                filmsRequest.ratingTo = roundedRatingFrom
            }
            DropDownExample(filmsRequest.yearFrom, {
                filmsRequest.yearFrom = it
            }, text = values.getString(R.string.filterChooseYearFilmTo))

            Spacer(modifier = Modifier.height(16.dp))

            DropDownExample(filmsRequest.yearTo, {
                filmsRequest.yearTo = it
            }, text = values.getString(R.string.filterChooseYearFilmAfto))

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                GradientButton(
                    text = values.getString(R.string.filterApply),
                    textColor = Color.Black,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Orange,
                            Red,
                            Orange
                        )
                    )
                ) {
                    if (copyFilmRequest == filmsRequest) {
                        navigationController.navigateUp()
                    } else {
                        val json = Uri.encode(Gson().toJson(filmsRequest))
                        navigationController.navigate("${MainRoute.ListFilm.value}/$json") {
                            popUpTo(navigationController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ChooseType(
    currentValue: MutableState<T>,
    onValueChange: (T) -> Unit,
    allValues: Array<Pair<T, LocalizedName>>,
    labelText: String
) {
    var expanded by remember { mutableStateOf(false) }
    val values = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = labelText,
            modifier = Modifier.padding(end = 8.dp),
            color = WhiteColor,
            style = styleTextBodyBig
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(1f)
        ) {
            TextField(
                value = values.getString((currentValue.value as LocalizedName).stringValueRes),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                allValues.forEach { (enumValue, names) ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = values.getString(names.stringValueRes),
                                style = styleTextBodyNormal
                            )
                        },
                        onClick = {
                            currentValue.value = enumValue
                            onValueChange(enumValue)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SliderWithIntValue(
    ratingFrom: Int,
    ratingTo: Int,
    onValueChangeFinished: (ratingTo: Int, ratingFrom: Int) -> Unit,
) {
    val values = LocalContext.current
    var sliderPosition by remember { mutableStateOf(ratingFrom.toFloat()..ratingTo.toFloat()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = values.getString(R.string.filterChooseRating),
            color = WhiteColor,
            style = styleTextBodyBig
        )
        Text(
            text = "${sliderPosition.start.roundToInt()}-${sliderPosition.endInclusive.roundToInt()}",
            color = WhiteColor,
            style = styleTextBodyBig
        )

        Spacer(modifier = Modifier.height(16.dp))
        RangeSlider(
            value = sliderPosition,
            onValueChange = { newValue ->
                sliderPosition = newValue
            },
            valueRange = 0f..10f,
            onValueChangeFinished = {
                onValueChangeFinished(
                    sliderPosition.start.roundToInt(),
                    sliderPosition.endInclusive.roundToInt()
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

//        RatingBar(
//            numOfStars = 5,
//            size = 30.dp,
//            spaceBetween = 3.dp,
//            value = sliderPosition.endInclusive - sliderPosition.start,
//            style = RatingBarStyle.Fill(),
//            onValueChange = { },
//            onRatingChanged = {}
//        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownExample(year: Int, onValueChange: (Int) -> Unit, text: String) {
    val options = (YEAR_TO..YEAR_FROM).map { it.toString() }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(year.toString()) }

    Text(
        color = WhiteColor,
        text = text,
        style = styleTextBodyBig
    )

    Spacer(modifier = Modifier.height(16.dp))

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {

        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption, style = styleTextBodyNormal) },
                    onClick = {
                        selectedOptionText = selectionOption
                        onValueChange(selectedOptionText.toInt())
                        expanded = false
                    },
                )
            }
        }
    }
}