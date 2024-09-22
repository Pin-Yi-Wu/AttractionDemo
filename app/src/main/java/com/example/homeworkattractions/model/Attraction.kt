package com.example.homeworkattractions.model

import java.io.Serializable

data class Attraction(
    val id: Int,
    val name: String,
    val name_zh: String?,
    val open_status: Int,
    val introduction: String,
    val open_time: String,
    val zipcode: String,
    val district: String,
    val address: String,
    val tel: String,
    val fax: String?,
    val email: String?,
    val months: String,
    val nlat: Double,
    val elong: Double,
    val official_site: String,
    val facebook: String?,
    val ticket: String?,
    val remind: String?,
    val staytime: String?,
    val modified: String,
    val url: String,
    val category: List<Category>,
    val target: List<Target>,
    val service: List<Service>,
    val friendly: List<Friendly>,
    val images: List<Image>,
    val files: List<AttractionFile>,
    val links: List<Link>
) : Serializable {
    override fun hashCode(): Int {
        return id.hashCode() * 31 +
                (name_zh?.hashCode() ?: 0) +
                (fax?.hashCode() ?: 0) +
                (email?.hashCode() ?: 0) +
                (facebook?.hashCode() ?: 0) +
                (ticket?.hashCode() ?: 0)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Attraction) return false

        return id == other.id &&
                name == other.name &&
                name_zh == other.name_zh &&
                open_status == other.open_status &&
                introduction == other.introduction &&
                open_time == other.open_time &&
                zipcode == other.zipcode &&
                district == other.district &&
                address == other.address &&
                tel == other.tel &&
                fax == other.fax &&
                email == other.email &&
                months == other.months &&
                nlat == other.nlat &&
                elong == other.elong &&
                official_site == other.official_site &&
                facebook == other.facebook &&
                ticket == other.ticket &&
                remind == other.remind &&
                staytime == other.staytime &&
                modified == other.modified &&
                url == other.url &&
                category == other.category &&
                target == other.target &&
                service == other.service &&
                friendly == other.friendly &&
                images == other.images &&
                files == other.files &&
                links == other.links
    }
}

data class Category(val id: Int, val name: String)
data class Target(val id: Int, val name: String)
data class Service(val id: Int, val name: String)
data class Friendly(val id: Int, val name: String)
data class Image(val src: String, val subject: String?, val ext: String)
data class AttractionFile(val id: Int, val name: String)
data class Link(val id: Int, val name: String)