package br.com.valecard.estabelecimento.ui.generic

import androidx.lifecycle.ViewModel
import com.studies.bibleapplication.ui.call.IProtocolResponse

abstract class GenericViewModel: ViewModel() {
    abstract fun setProtocol(viewProtocol: IProtocolResponse)
}