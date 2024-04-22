package com.jvg.peopleapp.people.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.timestampToDate
import com.jvg.peopleapp.core.presentation.ui.components.CustomClickableCard
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.RowComponent
import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

@Composable
fun ListPersonComponent(
    person: Person,
    showActive: Boolean = true,
    onSelect: (ObjectId) -> Unit,
    onActive: (ObjectId?, Boolean) -> Unit,
    onDelete: (Person) -> Unit
) {
    CustomClickableCard(
        onClick = { onSelect(person.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RowComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    5.dp,
                    Alignment.CenterHorizontally
                ),
                icon = painterResource(id = R.drawable.ic_fingerprint_24px),
                field = "Id",
                value = person.id.toHexString()
            )

            RowComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                field = "Fecha de creación",
                value = person.id.timestamp.timestampToDate(1),
                icon = painterResource(id = R.drawable.ic_calendar_month_24px)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.9f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
                ) {
                    RowComponent(
                        icon = painterResource(id = R.drawable.ic_person_24px),
                        field = "Nombre",
                        value = "${person.name} ${person.lastname}"
                    )

                    RowComponent(
                        icon = painterResource(id = R.drawable.ic_id_card_24px),
                        field = "Cédula",
                        value = person.code
                    )
                }
                Checkbox(
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(horizontal = 5.dp),
                    checked = person.active,
                    onCheckedChange = { _ ->
                        onActive(person.id, !person.active)
                    }
                )
            }

            if (!showActive) {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = stringResource(R.string.persona_inactiva),
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(all = 5.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = { onDelete(person) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
