package com.example.data.remote

import com.example.core.model.Source

interface SourcesRemote {
  suspend fun getSource(
      category: String, language: String, country: String
  ):List<Source>
}

