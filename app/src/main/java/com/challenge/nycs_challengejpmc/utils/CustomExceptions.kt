package com.challenge.nycs_challengejpmc.utils

class NullResponse(message: String = "The response is null"): Exception(message)
class FailureResponse(message: String?): Exception(message)