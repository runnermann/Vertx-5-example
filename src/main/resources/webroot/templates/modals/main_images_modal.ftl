<div class="modal fade modal-full-mobile modal-dialog-scrollable" id="main_images_modal" tabindex="-1"
     aria-labelledby="exampleModalLabel" aria-hidden="true" data-bs-keyboard="false">
    <div class="modal-dialog" id="imgwriterModalDialog">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="btn-close float-end" data-bs-dismiss="modal" aria-label="Close"></button>
                <div class="ani-container">
                    <#-- animated text-->
                    <div class="animate-form-question">
                        <div class="heading-box">
                            <#-- text animation in <p> >below -->
                            <div><span class="ani-typeWriterText" id="img-question"></span><span class="Typewriter__cursor" id="img_cursor">|</span></div>
                        </div>
                    </div>
                    <div class="form-hidden" id="img-uploader" onSubmit=preventDefault()>
                        <div class="align-items-center">
                            <span>Drag and Drop your photo below.</span>
                            <div class="drag-drop-area">
                                <input class="input-font input-fld"
                                       type="file"
                                       id="img-input"
                                       accept="image/*"
                                       required
                                       style="display: none;"
                                />
                                <span>(enter)</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer text-center" >
                <div class="bx col-md-12 form-hidden" id="divBottom">
                    <br>
                    <button class="btn-index-yellow modalBtn" id="goBtn">
                        SUBMIT
                        <svg width="0.825rem" viewBox="0 0 10 10" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M1 9L9 1M9 1H2.5M9 1V7.22222" stroke="currentColor" stroke-width="1.76" stroke-linecap="round" stroke-linejoin="round"></path></svg>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
