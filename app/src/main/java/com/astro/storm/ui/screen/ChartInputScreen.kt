package com.astro.storm.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.BikramSambatConverter
import com.astro.storm.data.localization.DateSystem
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalDateSystem
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.BirthData
import com.astro.storm.data.model.Gender
import com.astro.storm.ui.components.BSDatePickerDialog
import com.astro.storm.ui.components.LocationSearchField
import com.astro.storm.ui.components.TimezoneSelector
import com.astro.storm.ui.viewmodel.ChartUiState
import com.astro.storm.ui.viewmodel.ChartViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.astro.storm.ui.theme.LocalAppThemeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartInputScreen(
    viewModel: ChartViewModel,
    editChartId: Long? = null,
    onNavigateBack: () -> Unit,
    onChartCalculated: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val savedCharts by viewModel.savedCharts.collectAsState()
    val focusManager = LocalFocusManager.current
    val language = LocalLanguage.current
    val dateSystem = LocalDateSystem.current
    val colors = LocalAppThemeColors.current

    // Determine if we're in edit mode
    val isEditMode = editChartId != null

    // Find the chart to edit (if in edit mode)
    val chartToEdit = remember(editChartId, savedCharts) {
        editChartId?.let { id -> savedCharts.find { it.id == id } }
    }

    var name by rememberSaveable { mutableStateOf("") }
    var selectedGender by rememberSaveable { mutableStateOf(Gender.OTHER) }
    var locationLabel by rememberSaveable { mutableStateOf("") }
    var selectedDateMillis by rememberSaveable {
        mutableStateOf(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }
    var selectedHour by rememberSaveable { mutableStateOf(10) }
    var selectedMinute by rememberSaveable { mutableStateOf(0) }
    var latitude by rememberSaveable { mutableStateOf("") }
    var longitude by rememberSaveable { mutableStateOf("") }
    var selectedTimezone by rememberSaveable { mutableStateOf(ZoneId.systemDefault().id) }

    // Track if we've initialized from edit data (to prevent re-initialization)
    var hasInitializedFromEdit by rememberSaveable { mutableStateOf(false) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showBSDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    // Allow users to select which date picker to use (independent of global setting)
    var useBSPicker by remember { mutableStateOf(dateSystem == DateSystem.BS) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var errorKey by remember { mutableStateOf<StringKey?>(null) }
    var chartCalculationInitiated by remember { mutableStateOf(false) }
    // Guard to prevent duplicate saves - set to true after saveChart is called
    var chartSaveRequested by remember { mutableStateOf(false) }

    val selectedDate = remember(selectedDateMillis) {
        java.time.Instant.ofEpochMilli(selectedDateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
    val selectedTime = remember(selectedHour, selectedMinute) {
        LocalTime.of(selectedHour, selectedMinute)
    }

    val latitudeFocusRequester = remember { FocusRequester() }
    val longitudeFocusRequester = remember { FocusRequester() }

    // Pre-populate fields when editing an existing chart
    LaunchedEffect(chartToEdit) {
        if (chartToEdit != null && !hasInitializedFromEdit) {
            name = chartToEdit.name
            selectedGender = chartToEdit.gender
            locationLabel = chartToEdit.location
            selectedDateMillis = chartToEdit.dateTime.toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            selectedHour = chartToEdit.dateTime.hour
            selectedMinute = chartToEdit.dateTime.minute
            latitude = String.format(java.util.Locale.US, "%.6f", chartToEdit.latitude)
            longitude = String.format(java.util.Locale.US, "%.6f", chartToEdit.longitude)
            selectedTimezone = chartToEdit.timezone
            hasInitializedFromEdit = true
        }
    }

    LaunchedEffect(Unit) {
        if (!isEditMode) {
            viewModel.resetState()
        }
    }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is ChartUiState.Success -> {
                // Only save if calculation was initiated from this screen and save hasn't been requested yet
                if (chartCalculationInitiated && !chartSaveRequested) {
                    chartSaveRequested = true  // Prevent duplicate saves
                    viewModel.saveChart(state.chart)
                }
            }
            is ChartUiState.Saved -> {
                if (chartCalculationInitiated) {
                    chartCalculationInitiated = false
                    chartSaveRequested = false
                    onChartCalculated()
                }
            }
            is ChartUiState.Error -> {
                errorMessage = state.message
                showErrorDialog = true
                chartCalculationInitiated = false
                chartSaveRequested = false
            }
            else -> {}
        }
    }

    val scrollState = rememberScrollState()
    val isCalculating = uiState is ChartUiState.Calculating

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.ScreenBackground)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 32.dp)
        ) {
            ChartInputHeader(
                onNavigateBack = onNavigateBack,
                isEditMode = isEditMode
            )

            Spacer(modifier = Modifier.height(28.dp))

            IdentitySection(
                name = name,
                onNameChange = { name = it },
                selectedGender = selectedGender,
                onGenderChange = { selectedGender = it },
                onFocusNext = { focusManager.clearFocus() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LocationSearchField(
                value = locationLabel,
                onValueChange = { locationLabel = it },
                onLocationSelected = { location, lat, lon ->
                    locationLabel = location
                    latitude = String.format(java.util.Locale.US, "%.6f", lat)
                    longitude = String.format(java.util.Locale.US, "%.6f", lon)
                },
                label = stringResource(StringKey.INPUT_LOCATION),
                placeholder = stringResource(StringKey.INPUT_SEARCH_LOCATION)
            )

            Spacer(modifier = Modifier.height(28.dp))

            DateTimeSection(
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                selectedTimezone = selectedTimezone,
                useBSPicker = useBSPicker,
                language = language,
                onShowDatePicker = {
                    if (useBSPicker) {
                        showBSDatePicker = true
                    } else {
                        showDatePicker = true
                    }
                },
                onShowTimePicker = { showTimePicker = true },
                onTimezoneSelected = { selectedTimezone = it },
                onToggleDateSystem = { useBSPicker = !useBSPicker }
            )

            Spacer(modifier = Modifier.height(28.dp))

            CoordinatesSection(
                latitude = latitude,
                longitude = longitude,
                onLatitudeChange = { latitude = it },
                onLongitudeChange = { longitude = it },
                latitudeFocusRequester = latitudeFocusRequester,
                longitudeFocusRequester = longitudeFocusRequester,
                onDone = { focusManager.clearFocus() }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Extract localized string outside the lambda (stringResource is @Composable)
            val unknownText = stringResource(StringKeyMatch.MISC_UNKNOWN)

            GenerateButton(
                isCalculating = isCalculating,
                isEditMode = isEditMode,
                onClick = {
                    val validationKey = validateInputLocalized(latitude, longitude)
                    if (validationKey != null) {
                        errorKey = validationKey
                        showErrorDialog = true
                        return@GenerateButton
                    }

                    // Use parseCoordinate to handle degree symbols and other formats
                    val lat = parseCoordinate(latitude) ?: return@GenerateButton
                    val lon = parseCoordinate(longitude) ?: return@GenerateButton
                    val dateTime = LocalDateTime.of(selectedDate, selectedTime)

                    val birthData = BirthData(
                        name = name.ifBlank { unknownText },
                        dateTime = dateTime,
                        latitude = lat,
                        longitude = lon,
                        timezone = selectedTimezone,
                        location = locationLabel.ifBlank { unknownText },
                        gender = selectedGender
                    )

                    chartCalculationInitiated = true
                    if (isEditMode && editChartId != null) {
                        // Update existing chart
                        viewModel.calculateChartForUpdate(birthData, editChartId)
                    } else {
                        // Create new chart
                        viewModel.calculateChart(birthData)
                    }
                }
            )
        }
    }

    if (showDatePicker) {
        ChartDatePickerDialog(
            initialDateMillis = selectedDateMillis,
            onDismiss = { showDatePicker = false },
            onConfirm = { millis ->
                selectedDateMillis = millis
                showDatePicker = false
            }
        )
    }

    // BS Date Picker (when date system is BS)
    if (showBSDatePicker) {
        val currentBSDate = remember(selectedDate) {
            BikramSambatConverter.toBS(selectedDate) ?: BikramSambatConverter.today()
        }
        BSDatePickerDialog(
            initialDate = currentBSDate,
            onDismiss = { showBSDatePicker = false },
            onConfirm = { bsDate ->
                // Convert BS to AD and update selectedDateMillis
                BikramSambatConverter.toAD(bsDate)?.let { adDate ->
                    selectedDateMillis = adDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                }
                showBSDatePicker = false
            }
        )
    }

    if (showTimePicker) {
        ChartTimePickerDialog(
            initialHour = selectedHour,
            initialMinute = selectedMinute,
            onDismiss = { showTimePicker = false },
            onConfirm = { hour, minute ->
                selectedHour = hour
                selectedMinute = minute
                showTimePicker = false
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
                errorKey = null
            },
            title = {
                Text(
                    stringResource(StringKey.ERROR_INPUT),
                    color = colors.TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(
                    errorKey?.let { stringResource(it) } ?: errorMessage,
                    color = colors.TextSecondary
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showErrorDialog = false
                    errorKey = null
                }) {
                    Text(stringResource(StringKey.BTN_OK), color = colors.AccentPrimary)
                }
            },
            containerColor = colors.CardBackground,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

/**
 * Parse coordinate string, removing common symbols like degree (°), apostrophe ('), etc.
 * Supports formats: "27.7", "27.7°", "27°42'", "-27.7", etc.
 */
private fun parseCoordinate(value: String): Double? {
    // Remove common coordinate symbols and whitespace
    val cleaned = value.trim()
        .replace("°", "")
        .replace("'", "")
        .replace("\"", "")
        .replace("′", "")  // Unicode prime
        .replace("″", "")  // Unicode double prime
        .replace(",", ".")  // Handle comma as decimal separator
        .trim()

    return cleaned.toDoubleOrNull()
}

private fun validateInput(latitude: String, longitude: String): String? {
    val lat = parseCoordinate(latitude)
    val lon = parseCoordinate(longitude)

    return when {
        lat == null || lon == null -> "Please enter valid latitude and longitude"
        lat < -90 || lat > 90 -> "Latitude must be between -90 and 90"
        lon < -180 || lon > 180 -> "Longitude must be between -180 and 180"
        else -> null
    }
}

private fun validateInputLocalized(latitude: String, longitude: String): StringKey? {
    val lat = parseCoordinate(latitude)
    val lon = parseCoordinate(longitude)

    return when {
        lat == null || lon == null -> StringKey.ERROR_INVALID_COORDS
        lat < -90 || lat > 90 -> StringKey.ERROR_LATITUDE_RANGE
        lon < -180 || lon > 180 -> StringKey.ERROR_LONGITUDE_RANGE
        else -> null
    }
}

@Composable
private fun ChartInputHeader(
    onNavigateBack: () -> Unit,
    isEditMode: Boolean = false
) {
    val colors = LocalAppThemeColors.current
    val goBackText = stringResource(StringKey.BTN_BACK)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.semantics { contentDescription = goBackText }
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = null,
                tint = colors.TextSecondary,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = if (isEditMode) stringResource(StringKey.INPUT_EDIT_CHART) else stringResource(StringKey.INPUT_NEW_CHART),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.TextPrimary,
            letterSpacing = 0.3.sp
        )
    }
}

@Composable
private fun IdentitySection(
    name: String,
    onNameChange: (String) -> Unit,
    selectedGender: Gender,
    onGenderChange: (Gender) -> Unit,
    onFocusNext: () -> Unit
) {
    val colors = LocalAppThemeColors.current
    val language = LocalLanguage.current
    Column {
        SectionTitle(stringResource(StringKey.INPUT_IDENTITY))
        Spacer(modifier = Modifier.height(12.dp))

        ChartOutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = stringResource(StringKey.INPUT_FULL_NAME),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onFocusNext() })
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(StringKey.INPUT_GENDER),
            fontSize = 14.sp,
            color = colors.TextSecondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Gender.entries.forEach { gender ->
                GenderChip(
                    text = gender.getLocalizedName(language),
                    isSelected = selectedGender == gender,
                    onClick = { onGenderChange(gender) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun DateTimeSection(
    selectedDate: LocalDate,
    selectedTime: LocalTime,
    selectedTimezone: String,
    useBSPicker: Boolean,
    language: Language,
    onShowDatePicker: () -> Unit,
    onShowTimePicker: () -> Unit,
    onTimezoneSelected: (String) -> Unit,
    onToggleDateSystem: () -> Unit
) {
    // Format date based on selected picker type
    val dateDisplayText = remember(selectedDate, useBSPicker, language) {
        if (useBSPicker) {
            BikramSambatConverter.toBS(selectedDate)?.format(language)
                ?: selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } else {
            selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
    }

    val selectDateText = stringResource(StringKey.INPUT_SELECT_DATE)
    val selectTimeText = stringResource(StringKey.INPUT_SELECT_TIME)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionTitle(stringResource(StringKey.INPUT_DATE_TIME))

            // Date System Toggle (AD / BS)
            DateSystemToggle(
                useBSPicker = useBSPicker,
                language = language,
                onToggle = onToggleDateSystem
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DateTimeChip(
                text = dateDisplayText,
                onClick = onShowDatePicker,
                modifier = Modifier.weight(1f),
                contentDescription = selectDateText
            )
            DateTimeChip(
                text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                onClick = onShowTimePicker,
                modifier = Modifier.weight(0.7f),
                contentDescription = selectTimeText
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Use the new searchable timezone selector
        TimezoneSelector(
            selectedTimezone = selectedTimezone,
            onTimezoneSelected = onTimezoneSelected
        )
    }
}

/**
 * Toggle component for switching between AD and BS date input
 */
@Composable
private fun DateSystemToggle(
    useBSPicker: Boolean,
    language: Language,
    onToggle: () -> Unit
) {
    val colors = LocalAppThemeColors.current
    val adLabel = when (language) {
        Language.ENGLISH -> "AD"
        Language.NEPALI -> "ई.सं."
    }
    val bsLabel = when (language) {
        Language.ENGLISH -> "BS"
        Language.NEPALI -> "वि.सं."
    }

    Surface(
        onClick = onToggle,
        shape = RoundedCornerShape(16.dp),
        color = colors.ChipBackground,
        border = BorderStroke(1.dp, colors.BorderColor)
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // AD option
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (!useBSPicker) colors.AccentPrimary else Color.Transparent,
                modifier = Modifier.padding(1.dp)
            ) {
                Text(
                    text = adLabel,
                    fontSize = 12.sp,
                    fontWeight = if (!useBSPicker) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (!useBSPicker) colors.ButtonText else colors.TextSecondary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            // BS option
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (useBSPicker) colors.AccentPrimary else Color.Transparent,
                modifier = Modifier.padding(1.dp)
            ) {
                Text(
                    text = bsLabel,
                    fontSize = 12.sp,
                    fontWeight = if (useBSPicker) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (useBSPicker) colors.ButtonText else colors.TextSecondary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun CoordinatesSection(
    latitude: String,
    longitude: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    latitudeFocusRequester: FocusRequester,
    longitudeFocusRequester: FocusRequester,
    onDone: () -> Unit
) {
    Column {
        SectionTitle(stringResource(StringKey.INPUT_COORDINATES))
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ChartOutlinedTextField(
                value = latitude,
                onValueChange = onLatitudeChange,
                label = stringResource(StringKey.INPUT_LATITUDE),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(latitudeFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { longitudeFocusRequester.requestFocus() }
                )
            )

            ChartOutlinedTextField(
                value = longitude,
                onValueChange = onLongitudeChange,
                label = stringResource(StringKey.INPUT_LONGITUDE),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(longitudeFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onDone() }
                )
            )
        }
    }
}

@Composable
private fun GenerateButton(
    isCalculating: Boolean,
    isEditMode: Boolean = false,
    onClick: () -> Unit
) {
    val colors = LocalAppThemeColors.current
    val buttonText = if (isEditMode) stringResource(StringKey.BTN_UPDATE_SAVE) else stringResource(StringKey.BTN_GENERATE_SAVE)
    val buttonContentDesc = buttonText
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .semantics { contentDescription = buttonContentDesc },
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.ButtonBackground,
            contentColor = colors.ButtonText,
            disabledContainerColor = colors.ButtonBackground.copy(alpha = 0.5f),
            disabledContentColor = colors.ButtonText.copy(alpha = 0.5f)
        ),
        enabled = !isCalculating
    ) {
        Crossfade(targetState = isCalculating, label = "button_content") { calculating ->
            if (calculating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = colors.ButtonText,
                    strokeWidth = 2.dp
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        buttonText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartDatePickerDialog(
    initialDateMillis: Long,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    val colors = LocalAppThemeColors.current
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let(onConfirm)
                }
            ) {
                Text(stringResource(StringKey.BTN_OK), color = colors.AccentPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(StringKey.BTN_CANCEL), color = colors.TextSecondary)
            }
        },
        colors = DatePickerDefaults.colors(containerColor = colors.CardBackground)
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = colors.CardBackground,
                titleContentColor = colors.TextPrimary,
                headlineContentColor = colors.TextPrimary,
                weekdayContentColor = colors.TextSecondary,
                subheadContentColor = colors.TextSecondary,
                yearContentColor = colors.TextPrimary,
                currentYearContentColor = colors.AccentPrimary,
                selectedYearContentColor = colors.ButtonText,
                selectedYearContainerColor = colors.AccentPrimary,
                dayContentColor = colors.TextPrimary,
                selectedDayContentColor = colors.ButtonText,
                selectedDayContainerColor = colors.AccentPrimary,
                todayContentColor = colors.AccentPrimary,
                todayDateBorderColor = colors.AccentPrimary,
                navigationContentColor = colors.TextSecondary
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartTimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val colors = LocalAppThemeColors.current
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = colors.CardBackground,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(StringKey.INPUT_SELECT_TIME),
                    color = colors.TextSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        containerColor = colors.CardBackground,
                        clockDialColor = colors.ChipBackground,
                        clockDialSelectedContentColor = colors.ButtonText,
                        clockDialUnselectedContentColor = colors.TextPrimary,
                        selectorColor = colors.AccentPrimary,
                        periodSelectorBorderColor = colors.BorderColor,
                        periodSelectorSelectedContainerColor = colors.AccentPrimary,
                        periodSelectorUnselectedContainerColor = colors.CardBackground,
                        periodSelectorSelectedContentColor = colors.ButtonText,
                        periodSelectorUnselectedContentColor = colors.TextSecondary,
                        timeSelectorSelectedContainerColor = colors.AccentPrimary,
                        timeSelectorUnselectedContainerColor = colors.ChipBackground,
                        timeSelectorSelectedContentColor = colors.ButtonText,
                        timeSelectorUnselectedContentColor = colors.TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(StringKey.BTN_CANCEL), color = colors.TextSecondary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = { onConfirm(timePickerState.hour, timePickerState.minute) }
                    ) {
                        Text(stringResource(StringKey.BTN_OK), color = colors.AccentPrimary)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    val colors = LocalAppThemeColors.current
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = colors.TextPrimary,
        letterSpacing = 0.5.sp
    )
}

@Composable
private fun ChartOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val colors = LocalAppThemeColors.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = colors.TextSecondary, fontSize = 14.sp) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = chartTextFieldColors(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
    )
}

@Composable
private fun chartTextFieldColors(): TextFieldColors {
    val colors = LocalAppThemeColors.current
    return OutlinedTextFieldDefaults.colors(
        focusedTextColor = colors.TextPrimary,
        unfocusedTextColor = colors.TextPrimary,
        focusedBorderColor = colors.AccentPrimary,
        unfocusedBorderColor = colors.BorderColor,
        focusedLabelColor = colors.AccentPrimary,
        unfocusedLabelColor = colors.TextSecondary,
        cursorColor = colors.AccentPrimary,
        focusedTrailingIconColor = colors.TextSecondary,
        unfocusedTrailingIconColor = colors.TextSecondary
    )
}

@Composable
private fun DateTimeChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val colors = LocalAppThemeColors.current
    Surface(
        onClick = onClick,
        modifier = modifier
            .height(52.dp)
            .then(
                if (contentDescription != null) {
                    Modifier.semantics { this.contentDescription = contentDescription }
                } else Modifier
            ),
        shape = RoundedCornerShape(26.dp),
        color = colors.ChipBackground,
        border = BorderStroke(1.dp, colors.BorderColor)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                color = colors.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun GenderChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppThemeColors.current
    Surface(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) colors.AccentPrimary else colors.ChipBackground,
        border = BorderStroke(1.dp, if (isSelected) colors.AccentPrimary else colors.BorderColor)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                color = if (isSelected) colors.ButtonText else colors.TextPrimary,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}